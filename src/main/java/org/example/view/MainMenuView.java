package org.example.view;

import org.example.io.OutputDisplay;
import org.example.model.*;
import org.example.model.dto.AssetDTO;
import org.example.util.RegexUtils;
import org.example.view.factories.AssetFactory;
import org.example.view.factories.BookFactory;
import org.example.view.factories.MagazineFactory;
import org.example.view.factories.ThesisFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainMenuView extends MenuView{
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Add asset", "Add an asset to the library", this::addAssetCommand),
            new CommandTemplate("Remove asset", "Remove an asset from the library", this::removeAssetCommand),
            new CommandTemplate("Get borrowable assets", "Get all assets that are borrowable", this::getBorrowableAssetsStatusCommand),
            new CommandTemplate("Borrow asset", "Borrow an asset of library", this::borrowAssetCommand),
            new CommandTemplate("Return asset", "Return the asset of library", this::returnAssetCommand),
            new CommandTemplate("Get all assets", "Get all assets in the library", this::getAllAssetsCommand),
            new CommandTemplate("Get assets by title", "Get all assets with a specific title", this::getAssetsByTitleCommand),
            new CommandTemplate("Get assets by type", "Get all assets of a specific type", this::getAssetsByTypeCommand),
    };

    private final static List<Class<? extends Asset>> assetClasses = List.of(
            Book.class,
            Magazine.class,
            Thesis.class
    );
    private final static Map<String, AssetFactory> assetFactories = Map.of(
            Book.class.getSimpleName(), new BookFactory(),
            Magazine.class.getSimpleName(), new MagazineFactory(),
            Thesis.class.getSimpleName(), new ThesisFactory()
    );

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

        var assetDTOS = library.getAssetsByTitle(title);

        if (assetDTOS.isEmpty()){
            return "No assets found";
        }

        return convertAssetDTOListToHumanReadableString(assetDTOS);
    }

    private String getAllAssetsCommand() {
        var assets = library.getAssets();

        if (assets.isEmpty()){
            return "There are no assets in library";
        }

        return convertAssetDTOListToHumanReadableString(assets);
    }


    private String addAssetCommand() {
        System.out.println("Here is the list of all asset types: ");
        printAssetTypes();
        System.out.println("Enter asset Type: ");
        var assetType = scanner.nextLine();

        if (!assetFactories.containsKey(assetType)) {
            return "invalid asset type: " + assetType;
        }

        var assetFactory = assetFactories.get(assetType);

        try {
            var asset = assetFactory.createAsset(scanner);
            return library.addAsset(asset);
        } catch (RuntimeException e){
            return "An error occurred while creating/adding the asset: " + e.getMessage();
        }
    }

    private String removeAssetCommand(){
        String assetId = getAssetFromUserAndReturnAssetId();

        if (assetId == null){
            return "No asset found or selected.";
        }

        return library.removeAssetById(assetId);
    }

    private String getAssetFromUserAndReturnAssetId(){
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
                System.out.println((i + 1) + ". " + assets.get(i).description());
            }
            System.out.println((assets.size() + 1) + ". " + "Cancel");

            choice = scanner.nextInt();
            scanner.nextLine();
        } while (choice < 1 || choice > assets.size() + 1);

        if (choice == assets.size() + 1){
            return null;
        }

        return assets.get(choice - 1).id();
    }

    private String getAssetsByTypeCommand(){
        System.out.println("Here is the list of all asset types: ");
        printAssetTypes();
        System.out.println("Enter asset type: ");
        String type = scanner.nextLine();

        var assetDTOS = library.getAssetsByType(type);

        if (assetDTOS.isEmpty()){
            return "No assets found with type: " + type;
        }

        return convertAssetDTOListToHumanReadableString(assetDTOS);
    }

    private String borrowAssetCommand(){
        var assetId = getAssetFromUserAndReturnAssetId();
        if (assetId == null){
            return "No asset found or selected";
        }

        System.out.println("Enter return date (yyyy-mm-dd): ");
        String returnDateString = scanner.nextLine();
        LocalDate returnDate = RegexUtils.parseDate(returnDateString);

        if (returnDate == null){
            return "Invalid date format";
        }

        return library.borrowAssetById(assetId, returnDate);
    }

    private String returnAssetCommand(){
        var assetId = getAssetFromUserAndReturnAssetId();
        if (assetId == null){
            return "No asset found or selected";
        }

        return library.returnAssetById(assetId);
    }

    private String getBorrowableAssetsStatusCommand(){
        ArrayList<AssetDTO> borrowableAssetsString = library.getBorrowableAssets();

        if (borrowableAssetsString.isEmpty()){
            return "No borrowable assets found";
        }

        return convertAssetDTOListToHumanReadableString(borrowableAssetsString);
    }

    private static void printAssetTypes(){
        for (var assetClass : assetClasses) {
            System.out.println(assetClass.getSimpleName());
        }
    }

    private static String convertAssetDTOListToHumanReadableString(List<AssetDTO> assetDTOS){
        StringBuilder output = new StringBuilder();
        for (AssetDTO assetDTO : assetDTOS) {
            output.append(assetDTO.description());
            output.append("\n");
        }

        return output.toString();
    }
}