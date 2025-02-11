package org.example;

import org.example.io.CLIOutputDisplay;
import org.example.io.FileOutputDisplay;
import org.example.model.Library;
import org.example.view.MainMenuView;

public class Main {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/assets.json";
    private static final String outputFilePath = "target/output.txt";

    private final static boolean debugMode = true;

    private static AssetLoader assetLoader;


    public static void main(String[] args) throws Exception {
        initializeFields();
        var library = loadLibrary();

        // Create shutdown hook to save program data
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveLibrary(library);
        }));

        var outputDisplay = debugMode ? new CLIOutputDisplay() : new FileOutputDisplay(outputFilePath);
        MainMenuView mainMenuView = new MainMenuView(library, outputDisplay);
        mainMenuView.run();
    }

    private static void saveLibrary(Library library) {
        assetLoader.writeAssetsToFile(library.getAllAssetObjects());
    }

    private static void initializeFields() {
        assetLoader = new AssetLoader(booksDatabaseFilePath);
    }

    private static Library loadLibrary() {
        var assets = assetLoader.readAssetsFromFile();
        return new Library(assets);
    }
}
