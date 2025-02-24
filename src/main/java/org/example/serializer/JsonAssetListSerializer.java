package org.example.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.exception.InvalidAssetFileFormatException;
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
    public List<Asset> deserialize(byte[] data) throws InvalidAssetFileFormatException {
        var typeToken = new TypeToken<ArrayList<Asset>>() {
        };
        
        try {
            return gson.fromJson(new String(data, StandardCharsets.UTF_8), typeToken);
        } catch (JsonSyntaxException e) {
            throw new InvalidAssetFileFormatException("Invalid JSON format");
        }
    }
}
