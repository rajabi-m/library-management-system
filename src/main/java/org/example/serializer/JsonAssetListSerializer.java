package org.example.serializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Asset;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonAssetListSerializer implements Serializer<List<Asset>> {
    private final Gson gson = GsonProvider.getGson();
    @Override
    public byte[] serialize(List<Asset> assets) {
        return gson.toJson(assets).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public List<Asset> deserialize(byte[] data) {
        var typeToken = new TypeToken<ArrayList<Asset>>() {};
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), typeToken);
    }
}
