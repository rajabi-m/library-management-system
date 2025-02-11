package org.example;

import org.example.model.Asset;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssetLoader {
    private final String filePath;

    private final Serializer<List<Asset>> serializer = new JsonAssetListSerializer();
    public AssetLoader(String filePath) {
        this.filePath = filePath;
    }

    public void writeAssetsToFile(List<Asset> assets) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            String data = serializer.serialize(assets);
            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Asset> readAssetsFromFile() {
        try {
            FileReader fileReader = new FileReader(filePath);
            Scanner scanner = new Scanner(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            String data = stringBuilder.toString();

            var assets = serializer.deserialize(data);

            fileReader.close();
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
