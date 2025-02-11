package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Asset;
import org.example.util.GsonProvider;

import java.util.ArrayList;
import java.util.List;

public class JsonAssetListSerializer implements Serializer<List<Asset>> {
    private final Gson gson = GsonProvider.getGson();
    @Override
    public String serialize(List<Asset> assets) {
        return gson.toJson(assets);
    }

    @Override
    public List<Asset> deserialize(String data) {
        var typeToken = new TypeToken<ArrayList<Asset>>() {};
        return gson.fromJson(data, typeToken);
    }
}
