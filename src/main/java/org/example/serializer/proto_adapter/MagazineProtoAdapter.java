package org.example.serializer.proto_adapter;

import com.example.model.proto.ProtoMessages;
import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.Magazine;

import java.time.LocalDate;

public class MagazineProtoAdapter implements ProtoAdapter<Asset> {
    @Override
    public ProtoMessages.Asset toProto(Asset asset) {
        if (!(asset instanceof Magazine magazine)){
            throw new IllegalArgumentException("Asset is not a magazine");
        }
        return ProtoMessages.Asset.newBuilder().setMagazine(ProtoMessages.Magazine.newBuilder()
                .setId(magazine.getId())
                .setTitle(magazine.getTitle())
                .setPublisher(magazine.getPublisher())
                .setReleaseDate(magazine.getReleaseDate())
                .setStatus(ProtoMessages.AssetStatus.valueOf(magazine.getStatus().name()))
                .setReturnDate(magazine.getReturnDate().toEpochDay())
                .build()).build();
    }

    @Override
    public Magazine fromProto(ProtoMessages.Asset proto) {
        ProtoMessages.Magazine magazineProto = proto.getMagazine();
        return new Magazine(
                magazineProto.getId(),
                magazineProto.getTitle(),
                magazineProto.getPublisher(),
                magazineProto.getReleaseDate(),
                AssetStatus.valueOf(magazineProto.getStatus().name()),
                LocalDate.ofEpochDay(magazineProto.getReturnDate())
        );
    }
}
