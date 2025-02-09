package org.example;

import org.example.io.CLIOutputDisplay;
import org.example.io.FileOutputDisplay;
import org.example.model.*;
import org.example.view.MainMenuView;

public class Main {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/assets.txt";
    private static final String outputFilePath = "target/output.txt";

    private final static boolean debugMode = true;

    private static AssetLoader assetLoader;


    public static void main(String[] args) {
        initializeFields();

        var library = loadLibrary();
        var outputDisplay = debugMode ? new CLIOutputDisplay() : new FileOutputDisplay(outputFilePath);
        MainMenuView mainMenuView = new MainMenuView(library, outputDisplay);
        mainMenuView.run();

        saveLibrary(library);
    }

    private static void saveLibrary(Library library) {
        assetLoader.writeAssetsToFile(library.getAssets());
    }

    private static void initializeFields() {
        assetLoader = new AssetLoader(booksDatabaseFilePath);
    }

    private static Library loadLibrary() {
        LinkedList<Asset> assets = assetLoader.readAssetsFromFile();
        return new Library(assets);
    }
}
