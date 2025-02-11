package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Asset;
import org.example.utils.GsonProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AssetLoader {
    private final String filePath;

    private final static Gson gson = GsonProvider.getGson();

    public AssetLoader(String filePath) {
        this.filePath = filePath;
    }

    public void writeAssetsToFile(Iterable<Asset> assets) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            String json = gson.toJson(assets);
            bufferedWriter.write(json);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Asset> readAssetsFromFile() {
        var assets = new ArrayList<Asset>();
        try {
            FileReader fileReader = new FileReader(filePath);
            Scanner scanner = new Scanner(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            String json = stringBuilder.toString();

            var typeToken = new TypeToken<ArrayList<Asset>>() {};
            assets = gson.fromJson(json, typeToken);

            fileReader.close();
            System.out.println("Assets loaded successfully.");
        } catch (FileNotFoundException e){
            System.out.println("Assets file not found. Creating a new file...");
            createAssetsFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return assets == null ? new ArrayList<>() : assets;
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
