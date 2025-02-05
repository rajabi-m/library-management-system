package org.example.view;

import org.example.AssetLoader;
import org.example.io.OutputDisplay;
import org.example.model.*;

import java.util.Scanner;

public class MainMenuView extends MenuView{
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Add asset", "Add an asset to the library", this::addAssetCommand),
            new CommandTemplate("Remove asset", "Remove an asset from the library", this::removeAssetCommand),
            new CommandTemplate("Update asset status", "Update the status of an asset", this::updateAssetStatusCommand),
            new CommandTemplate("Get all assets", "Get all assets in the library", this::getAllAssetsCommand),
            new CommandTemplate("Get assets by title", "Get all assets with a specific title", this::getAssetsByTitleCommand),
            new CommandTemplate("Get assets by type", "Get all assets of a specific type", this::getAssetsByTypeCommand),
    };

    private final Library library;

    public MainMenuView(Library library, OutputDisplay outputDisplay){
        super(new Scanner(System.in), outputDisplay);
        addCommands(commands);
        this.library = library;
    }

    @Override
    protected void onMenuInitialize() {
        System.out.println(welcomeText);
    }


    private String getAssetsByTitleCommand() {
        System.out.println("Enter asset title: ");
        String title = scanner.nextLine();

        var assets = library.getAssetsByTitle(title);

        return assets.toString();
    }

    private String getAllAssetsCommand() {
        return library.getAssets().toString();
    }


    private String addAssetCommand() {
        System.out.println("Enter asset in csv format: ");
        String assetCsv = scanner.nextLine();

        var asset = AssetLoader.convertCsvToAsser(assetCsv);

        return library.addAsset(asset);
    }

    private String removeAssetCommand(){
        Asset asset = getAssetFromUser();

        if (asset == null){
            return "No asset found or selected.";
        }

        return library.removeAsset(asset);
    }

    private String updateAssetStatusCommand(){
        var asset = getAssetFromUser();

        if (asset == null){
            return "No asset found or selected.";
        }

        System.out.println("Enter new status: ");
        String status = scanner.nextLine();

        return library.updateAssetStatus(asset, AssetStatus.valueOf(status));
    }

    private Asset getAssetFromUser(){
        System.out.println("Enter asset title: ");
        String title = scanner.nextLine();

        var assets = library.queryAssets(title);

        if (assets.isEmpty()){
            return null;
        }

        int choice;
        do {
            System.out.println("Select asset: ");
            for (int i = 0; i < assets.size(); i++) {
                System.out.println((i + 1) + ". " + assets.get(i));
            }
            System.out.println((assets.size() + 1) + ". " + "Cancel");

            choice = scanner.nextInt();
            scanner.nextLine();
        } while (choice < 1 || choice > assets.size() + 1);

        if (choice == assets.size() + 1){
            return null;
        }

        return assets.get(choice - 1);
    }

    private String getAssetsByTypeCommand(){
        System.out.println("Enter asset type: ");
        String type = scanner.nextLine();

        var assets = library.getAssetsByType(type);

        if (assets.isEmpty()){
            return "No assets found with type: " + type;
        }

        return assets.toString();
    }
}