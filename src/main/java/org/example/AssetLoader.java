package org.example;

import org.example.model.Asset;
import org.example.serializer.JsonAssetListSerializer;
import org.example.serializer.ProtoAssetListSerializer;
import org.example.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssetLoader {
    private final String filePath;

    private final Serializer<List<Asset>> serializer = new ProtoAssetListSerializer();
    public AssetLoader(String filePath) {
        this.filePath = filePath;
    }

    public void writeAssetsToFile(List<Asset> assets) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filePath))) {
            var data = serializer.serialize(assets);
            dataOutputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Asset> readAssetsFromFile() {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            byte[] data = byteArrayOutputStream.toByteArray();
            var assets = serializer.deserialize(data);

            System.out.println("Assets loaded successfully.");
            return assets == null ? new ArrayList<>() : assets;
        } catch (FileNotFoundException e){
            System.out.println("Assets file not found. Creating a new file...");
            createAssetsFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    private void createAssetsFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write("");
            bufferedWriter.close();
            System.out.println("File created successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
