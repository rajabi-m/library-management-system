package org.example;

import org.example.io.CLIOutputDisplay;
import org.example.io.FileOutputDisplay;
import org.example.model.Library;
import org.example.service.LibraryManagementService;
import org.example.service.requests.Request;
import org.example.service.response.Response;
import org.example.view.MainMenuView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    // using target/ folder to store files, so they are ignored by git
    private static final String booksDatabaseFilePath = "target/assets.json";
    private static final String outputFilePath = "target/output.txt";

    private final static boolean debugMode = true;

    private static AssetLoader assetLoader;

    private final static BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final static BlockingQueue<Response<?>> responseQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        initializeFields();
        var library = loadLibrary();

        // Create shutdown hook to save program data
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveLibrary(library)));

        Thread libraryManagementThread = new Thread(new LibraryManagementService(library, requestQueue, responseQueue));
        libraryManagementThread.setDaemon(true);
        libraryManagementThread.start();

        var outputDisplay = debugMode ? new CLIOutputDisplay() : new FileOutputDisplay(outputFilePath);
        MainMenuView mainMenuView = new MainMenuView(outputDisplay, requestQueue, responseQueue);
        Thread mainMenuViewThread = new Thread(mainMenuView);
        mainMenuViewThread.start();
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
