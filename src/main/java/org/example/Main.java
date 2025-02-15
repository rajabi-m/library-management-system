package org.example;

import org.example.io.CLIOutputDisplay;
import org.example.io.FileOutputDisplay;
import org.example.model.Book;
import org.example.model.Library;
import org.example.model.Magazine;
import org.example.model.Thesis;
import org.example.service.LibraryManagementService;
import org.example.service.requests.Request;
import org.example.service.response.Response;
import org.example.view.MainMenuView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        LibraryManagementService libraryManagementService = new LibraryManagementService(library, requestQueue, responseQueue);
        executorService.submit(libraryManagementService);

        var outputDisplay = debugMode ? new CLIOutputDisplay() : new FileOutputDisplay(outputFilePath);
        MainMenuView mainMenuView = new MainMenuView(outputDisplay, requestQueue, responseQueue);
        executorService.submit(mainMenuView);

        executorService.shutdown();
    }

    private static void saveLibrary(Library library) {
        assetLoader.writeAssetsToFile(library.getAllAssetObjects());
    }

    private static void initializeFields() {
        assetLoader = new AssetLoader(booksDatabaseFilePath);
    }

    private static Library loadLibrary() {
        var assets = assetLoader.readAssetsFromFile();
        var library = Library.getInstance();
        library.addAllAssets(assets);
        generateTestData(library);
        return library;
    }

    private static void generateTestData(Library library) {
        if (!library.getAllAssetObjects().isEmpty()) return;

        var book1 = new Book("Art of War", "Sun Tzu", 1990);
        var book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925);
        var book3 = new Book("To Kill a Mockingbird", "Harper Lee", 1960);
        var book4 = new Book("1984", "George Orwell", 1949);
        var magazine1 = new Magazine("Daily News", "New York Times", "2021-09-01");
        var magazine2 = new Magazine("Tech Insider", "Business Insider", "2021-09-01");
        var magazine3 = new Magazine("Sports Weekly", "ESPN", "2021-09-01");
        var magazine4 = new Magazine("Fashion Weekly", "Vogue", "2021-09-01");
        var thesis1 = new Thesis("The Theory of Everything", "Stephen Hawking", "None", "Physics", "1988");
        var thesis2 = new Thesis("The Origin of Species", "Charles Darwin", "None", "Biology", "1859");
        var thesis3 = new Thesis("The Interpretation of Dreams", "Sigmund Freud", "None", "Psychology", "1899");
        var thesis4 = new Thesis("The Wealth of Nations", "Adam Smith", "None", "Economics", "1776");

        library.addAsset(book1);
        library.addAsset(book2);
        library.addAsset(book3);
        library.addAsset(book4);
        library.addAsset(magazine1);
        library.addAsset(magazine2);
        library.addAsset(magazine3);
        library.addAsset(magazine4);
        library.addAsset(thesis1);
        library.addAsset(thesis2);
        library.addAsset(thesis3);
        library.addAsset(thesis4);
    }
}
