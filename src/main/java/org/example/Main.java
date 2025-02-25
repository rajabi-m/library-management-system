package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.example.controller.ConnectionBridge;
import org.example.controller.LibraryController;
import org.example.exception.CannotConnectToDatabaseException;
import org.example.exception.ConfigFileNotFoundException;
import org.example.exception.GlobalExceptionHandler;
import org.example.exception.InvalidConfigFileFormatException;
import org.example.io.CLIOutputDisplay;
import org.example.io.FileOutputDisplay;
import org.example.model.Book;
import org.example.model.Magazine;
import org.example.model.Thesis;
import org.example.repository.DefaultAssetRepository;
import org.example.serializer.GsonProvider;
import org.example.serializer.JsonAssetListSerializer;
import org.example.serializer.ProtoAssetListSerializer;
import org.example.service.LibraryService;
import org.example.view.MainMenuView;

import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String configFilePath = "config.json";

    private static AssetLoader assetLoader;
    private final static ConnectionBridge connectionBridge = new ConnectionBridge();

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        loadConfigs();
        Configurator.setRootLevel(Level.valueOf(Config.getInstance().logLevel()));
        initializeFields();
        connectToDataBase();
        generateTestDataIfNeeded(LibraryService.getInstance());

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        LibraryController libraryController = new LibraryController(connectionBridge);
        executorService.submit(libraryController);

        var outputDisplay = Config.getInstance().saveOutputToFile()
                ? new FileOutputDisplay(Config.getInstance().outputFilePath())
                : new CLIOutputDisplay();
        MainMenuView mainMenuView = new MainMenuView(outputDisplay, connectionBridge);
        executorService.submit(mainMenuView);

        executorService.shutdown();
        executorService.close();
    }

    private static void loadConfigs() {
        // Use gson to load configurations
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(configFilePath)) {
            Gson gson = GsonProvider.getGson();
            assert inputStream != null;
            Reader reader = new InputStreamReader(inputStream);
            var config = gson.fromJson(reader, Config.class);
            Config.setInstance(config);
            reader.close();
        } catch (JsonSyntaxException e) {
            throw new InvalidConfigFileFormatException();
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException(configFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveAssets() {
        var libraryService = LibraryService.getInstance();
        assetLoader.writeAssetsToFile(libraryService.getAllAssetObjects());
    }

    private static void initializeFields() {
        var serializer = switch (Config.getInstance().assetSerializationFormat()) {
            case JSON -> new JsonAssetListSerializer();
            case ProtoBuf -> new ProtoAssetListSerializer();
        };
        assetLoader = new AssetLoader(Config.getInstance().assetsFilePath(), serializer);
    }

    private static void connectToDataBase() {
        try {
            DefaultAssetRepository.getInstance().connectToDatabase(
                    Config.getInstance().databaseUrl(),
                    Config.getInstance().databaseUser(),
                    Config.getInstance().databasePassword()
            );
        } catch (SQLException e) {
            throw new CannotConnectToDatabaseException(e.getMessage());
        }
    }

    private static void generateTestDataIfNeeded(LibraryService libraryService) {
        if (!libraryService.getAllAssets().isEmpty()) return;

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

        libraryService.addAsset(book1);
        libraryService.addAsset(book2);
        libraryService.addAsset(book3);
        libraryService.addAsset(book4);
        libraryService.addAsset(magazine1);
        libraryService.addAsset(magazine2);
        libraryService.addAsset(magazine3);
        libraryService.addAsset(magazine4);
        libraryService.addAsset(thesis1);
        libraryService.addAsset(thesis2);
        libraryService.addAsset(thesis3);
        libraryService.addAsset(thesis4);
    }
}
