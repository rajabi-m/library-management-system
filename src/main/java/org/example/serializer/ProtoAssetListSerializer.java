package org.example.serializer;

import com.example.model.proto.ProtoMessages;
import com.google.protobuf.InvalidProtocolBufferException;
import org.example.exception.InvalidAssetFileFormatException;
import org.example.model.Asset;
import org.example.model.Book;
import org.example.model.Magazine;
import org.example.model.Thesis;
import org.example.serializer.proto_adapter.BookProtoAdapter;
import org.example.serializer.proto_adapter.MagazineProtoAdapter;
import org.example.serializer.proto_adapter.ProtoAdapter;
import org.example.serializer.proto_adapter.ThesisProtoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProtoAssetListSerializer implements Serializer<List<Asset>> {
    private final static Map<String, ProtoAdapter<Asset>> protoSerializerAdapters = Map.of(
            Book.class.getSimpleName(), new BookProtoAdapter(),
            Magazine.class.getSimpleName(), new MagazineProtoAdapter(),
            Thesis.class.getSimpleName(), new ThesisProtoAdapter()
    );

    private final static Map<ProtoMessages.Asset.AssetCase, ProtoAdapter<Asset>> protoDeserializerAdapters = Map.of(
            ProtoMessages.Asset.AssetCase.BOOK, new BookProtoAdapter(),
            ProtoMessages.Asset.AssetCase.MAGAZINE, new MagazineProtoAdapter(),
            ProtoMessages.Asset.AssetCase.THESIS, new ThesisProtoAdapter()
    );

    @Override
    public byte[] serialize(List<Asset> assets) {
        ProtoMessages.AssetList.Builder assetListBuilder = ProtoMessages.AssetList.newBuilder();
        for (Asset asset : assets) {
            if (!protoSerializerAdapters.containsKey(asset.getType())) {
                throw new IllegalArgumentException("Unknown asset type");
            }

            var assetAdapter = protoSerializerAdapters.get(asset.getType());
            var protoAsset = assetAdapter.toProto(asset);
            assetListBuilder.addAssets(protoAsset);
        }

        return assetListBuilder.build().toByteArray();
    }

    @Override
    public List<Asset> deserialize(byte[] data) throws InvalidAssetFileFormatException {
        try {
            ProtoMessages.AssetList assetList = ProtoMessages.AssetList.parseFrom(data);
            ArrayList<Asset> assets = new ArrayList<>();
            for (ProtoMessages.Asset asset : assetList.getAssetsList()) {
                if (!protoDeserializerAdapters.containsKey(asset.getAssetCase())) {
                    throw new IllegalArgumentException("Unknown asset type");
                }
                var assetAdapter = protoDeserializerAdapters.get(asset.getAssetCase());
                assets.add(assetAdapter.fromProto(asset));
            }
            return assets;
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidAssetFileFormatException("Invalid Proto format");
        }
    }
}
