package org.example;

import org.example.model.*;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class AssetLoader {
    private final String filePath;
    private static final Map<String, Function<String, Asset>> assetParsers = Map.of(
            Book.class.getName(), Book::fromCsv,
            Magazine.class.getName(), Magazine::fromCsv
    );

    public AssetLoader(String filePath) {
        this.filePath = filePath;
    }

    public void writeAssetsToFile(Iterable<Asset> assets) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            for (var asset : assets) {
                bufferedWriter.write(asset.getClass().getName() + "," + asset.toCsv() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList<Asset> readAssetsFromFile() {
        var assets = new LinkedList<Asset>();
        try {
            FileReader fileReader = new FileReader(filePath);
            Scanner scanner = new Scanner(fileReader);

            while (scanner.hasNextLine()) {
                String fullCsv = scanner.nextLine();
                parseLine(fullCsv, assets);
            }

            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return assets;
    }

    private void parseLine(String fullCsv, LinkedList<Asset> assets) {
        int firstCommaIndex = fullCsv.indexOf(",");

        if (firstCommaIndex == -1) {
            throw new RuntimeException("Invalid CSV format");
        }

        String assetClassName = fullCsv.substring(0, firstCommaIndex);
        String csv = fullCsv.substring(firstCommaIndex + 1);

        var parser = assetParsers.get(assetClassName);
        if (parser == null) {
            throw new RuntimeException("Unsupported asset type: " + assetClassName);
        }

        assets.add(parser.apply(csv));
    }

}
