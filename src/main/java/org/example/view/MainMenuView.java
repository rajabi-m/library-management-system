package org.example.view;

import org.example.io.OutputDisplay;
import org.example.model.*;
import org.example.model.dto.AssetDTO;
import org.example.service.ConnectionBridge;
import org.example.service.requests.*;
import org.example.service.response.Response;
import org.example.util.ANSICodes;
import org.example.util.RegexUtils;
import org.example.view.factories.AssetFactory;
import org.example.view.factories.BookFactory;
import org.example.view.factories.MagazineFactory;
import org.example.view.factories.ThesisFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class MainMenuView extends MenuView {
    private static final String welcomeText = """
            welcome to the library!
            Please choose an option:
            """;
    private final CommandTemplate[] commands = {
            new CommandTemplate("Add asset", "Add an asset to the library", this::addAssetCommand),
            new CommandTemplate("Remove asset", "Remove an asset from the library", this::removeAssetCommand),
            new CommandTemplate("Update asset", "Update an asset in the library", this::updateAssetCommand),
            new CommandTemplate("Get borrowable assets", "Get all assets that are borrowable", this::getAllBorrowableAssetsCommand),
            new CommandTemplate("Borrow asset", "Borrow an asset of library", this::borrowAssetCommand),
            new CommandTemplate("Return asset", "Return the asset of library", this::returnAssetCommand),
            new CommandTemplate("Get all assets", "Get all assets in the library", this::getAllAssetsCommand),
            new CommandTemplate("Get assets by title", "Get all assets with a specific title", this::getAssetsByTitleCommand),
            new CommandTemplate("Get assets by type", "Get all assets of a specific type", this::getAssetsByTypeCommand),
    };

    private final static Logger logger = Logger.getLogger(MainMenuView.class.getSimpleName());

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

    private final ConnectionBridge connectionBridge;

    public MainMenuView(OutputDisplay outputDisplay, ConnectionBridge connectionBridge) {
        super(new Scanner(System.in), outputDisplay);
        this.connectionBridge = connectionBridge;
        addCommands(commands);
    }

    @Override
    public void onMenuClose() {
        Response<String> response = sendRequestAndWaitForResponse(new CloseServiceRequest());
        System.out.println(response.data());
    }

    @Override
    protected void onMenuInitialize() {
        System.out.println(welcomeText);
    }

    private <T> Response<T> sendRequestAndWaitForResponse(Request request) {
        return connectionBridge.sendRequestAndWaitForResponse(request);
    }

    private String getAssetsByTitleCommand() {
        System.out.println("Enter asset title: ");
        String title = scanner.nextLine();

        Response<List<AssetDTO>> response = sendRequestAndWaitForResponse(new GetAssetsByTitleRequest(title));

        var assetDTOS = response.data();

        if (assetDTOS.isEmpty()) {
            return "No assets found";
        }

        return convertAssetDTOListToHumanReadableString(assetDTOS);
    }

    private String getAllAssetsCommand() {
        Response<List<AssetDTO>> response = sendRequestAndWaitForResponse(new GetAllAssetsRequest());

        var assetDTOS = response.data();

        if (assetDTOS.isEmpty()) {
            return "There are no assets in library";
        }

        return convertAssetDTOListToHumanReadableString(assetDTOS);
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

            Response<String> response = sendRequestAndWaitForResponse(new AddAssetRequest(asset));

            return response.data();
        } catch (RuntimeException e) {
            return "An error occurred while creating/adding the asset: " + e.getMessage();
        }
    }

    private String removeAssetCommand() {
        AssetDTO assetDTO = getAssetFromUser();

        if (assetDTO == null) {
            return "No asset found or selected.";
        }

        var assetId = assetDTO.id();

        Response<String> response = sendRequestAndWaitForResponse(new RemoveAssetRequest(assetId));

        return response.data();
    }

    private AssetDTO getAssetFromUser() {
        System.out.println("Enter asset title: ");
        String query = scanner.nextLine();

        Response<List<AssetDTO>> response = sendRequestAndWaitForResponse(new QueryAssetsRequest(query));

        var assets = response.data();

        if (assets.isEmpty()) {
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

        if (choice == assets.size() + 1) {
            return null;
        }

        return assets.get(choice - 1);
    }

    private String getAssetsByTypeCommand() {
        System.out.println("Here is the list of all asset types: ");
        printAssetTypes();
        System.out.println("Enter asset type: ");
        String type = scanner.nextLine();

        Response<List<AssetDTO>> response = sendRequestAndWaitForResponse(new GetAssetsByTypeRequest(type));
        var assetDTOS = response.data();

        if (assetDTOS.isEmpty()) {
            return "No assets found with type: " + type;
        }

        return convertAssetDTOListToHumanReadableString(assetDTOS);
    }

    private String borrowAssetCommand() {
        var assetDTO = getAssetFromUser();
        if (assetDTO == null) {
            return "No asset found or selected";
        }

        var assetId = assetDTO.id();

        if (!assetDTO.borrowable()) {
            return assetNotBorrowableFlow(assetDTO);
        }

        System.out.println("Enter return date (yyyy-mm-dd): ");
        String returnDateString = scanner.nextLine();
        LocalDate returnDate = RegexUtils.parseDate(returnDateString);

        if (returnDate == null) {
            return "Invalid date format";
        }

        Response<String> response = sendRequestAndWaitForResponse(new BorrowAssetRequest(assetId, returnDate));

        return response.data();
    }

    private String assetNotBorrowableFlow(AssetDTO assetDTO) {
        System.out.println("Asset is not borrowable. Do you want to subscribe to it? (y/n)");
        String choice = scanner.nextLine();
        if (choice.equals("y")) {
            System.out.println("Enter subscriber username: ");
            String subscriber = scanner.nextLine();
            Request request = new SubscribeToAssetRequest(assetDTO.id(), new User(subscriber));
            Response<String> response = sendRequestAndWaitForResponse(request);
            return response.data();
        } else if (choice.equals("n")) {
            return "Asset is not borrowable";
        } else {
            return "Invalid choice";
        }
    }

    private String returnAssetCommand() {
        AssetDTO assetDTO = getAssetFromUser();
        if (assetDTO == null) {
            return "No asset found or selected";
        }
        var assetId = assetDTO.id();

        Response<String> response = sendRequestAndWaitForResponse(new ReturnAssetRequest(assetId));

        return response.data();
    }

    private String getAllBorrowableAssetsCommand() {
        Response<List<AssetDTO>> response = sendRequestAndWaitForResponse(new GetAllBorrowableAssetsRequest());
        List<AssetDTO> borrowableAssets = response.data();

        if (borrowableAssets.isEmpty()) {
            return "No borrowable assets found";
        }

        StringBuilder output = new StringBuilder();
        BiConsumer<String, Boolean> convertAssetToString = (assetDescription, borrowable) -> {
            output.append(borrowable ? ANSICodes.GREEN : ANSICodes.RED)
                    .append(assetDescription)
                    .append(ANSICodes.RESET)
                    .append("\n");
        };

        borrowableAssets.forEach(assetDTO ->
                convertAssetToString.accept(assetDTO.description(), assetDTO.borrowable())
        );

        return output.toString();
    }

    private String updateAssetCommand() {
        AssetDTO assetDTO = getAssetFromUser();
        if (assetDTO == null) {
            return "No asset found or selected";
        }

        if (!assetFactories.containsKey(assetDTO.type())) {
            return "Invalid asset type: " + assetDTO.type();
        }

        var assetFactory = assetFactories.get(assetDTO.type());
        var updatedAsset = assetFactory.createAsset(scanner);

        Response<String> updateResponse = sendRequestAndWaitForResponse(new UpdateAssetRequest(assetDTO.id(), updatedAsset));
        return updateResponse.data();
    }

    private static void printAssetTypes() {
        for (var assetClass : assetClasses) {
            System.out.println(assetClass.getSimpleName());
        }
    }

    private static String convertAssetDTOListToHumanReadableString(List<AssetDTO> assetDTOS) {
        StringBuilder output = new StringBuilder();
        assetDTOS.forEach(assetDTO -> {
            output.append(assetDTO.description());
            output.append("\n");
        });

        return output.toString();
    }
}