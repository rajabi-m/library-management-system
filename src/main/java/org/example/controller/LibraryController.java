package org.example.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Asset;
import org.example.model.dto.AssetDTO;
import org.example.service.LibraryService;
import org.example.service.requests.*;
import org.example.service.response.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LibraryController implements Runnable {
    private final LibraryService libraryService = LibraryService.getInstance();
    private boolean serviceRunning = true;
    private final ConnectionBridge connectionBridge;
    private final Logger logger = LogManager.getLogger("Library Controller");
    private final Map<Class<? extends Request>, Function<Request, Response<?>>> requestHandlers = new HashMap<>();

    public LibraryController(ConnectionBridge connectionBridge1) {
        this.connectionBridge = connectionBridge1;
        initializeRequestHandlers();
    }

    private void initializeRequestHandlers() {
        requestHandlers.put(AddAssetRequest.class, this::addAssetHandler);
        requestHandlers.put(RemoveAssetRequest.class, this::removeAssetHandler);
        requestHandlers.put(GetAllAssetsRequest.class, this::getAllAssetsHandler);
        requestHandlers.put(GetAssetsByTitleRequest.class, this::getAssetsByTitleHandler);
        requestHandlers.put(GetAllBorrowableAssetsRequest.class, this::getAllBorrowableAssetsHandler);
        requestHandlers.put(GetAssetsByTypeRequest.class, this::getAssetsByTypeRequest);
        requestHandlers.put(QueryAssetsRequest.class, this::queryAssetsHandler);
        requestHandlers.put(BorrowAssetRequest.class, this::borrowAssetHandler);
        requestHandlers.put(ReturnAssetRequest.class, this::returnAssetHandler);
        requestHandlers.put(UpdateAssetRequest.class, this::updateAssetHandler);
        requestHandlers.put(GetAssetByIdRequest.class, this::getAssetByIdHandler);
        requestHandlers.put(CloseServiceRequest.class, this::closeServiceHandler);
        requestHandlers.put(SubscribeToAssetRequest.class, this::subscribeToAssetHandler);
    }

    @Override
    public void run() {
        while (serviceRunning) {
            try {
                Request request = connectionBridge.takeRequest();
                logger.info("Received request: {}", request);

                Function<Request, Response<?>> handler = requestHandlers.getOrDefault(
                        request.getClass(),
                        this::handleUnsupportedRequest
                );

                Response<?> response = handler.apply(request);
                logger.info("Sending response: {}", response);
                connectionBridge.addResponse(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Response<String> closeServiceHandler(Request request) {
        serviceRunning = false;
        return new Response<>("Service closed successfully!");
    }

    private Response<String> addAssetHandler(Request request) {
        if (!(request instanceof AddAssetRequest addAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        Asset asset = addAssetRequest.getAsset();
        return Response.of(libraryService.addAsset(asset));
    }

    private Response<String> removeAssetHandler(Request request) {
        if (!(request instanceof RemoveAssetRequest removeAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = removeAssetRequest.getAssetId();
        return Response.of(libraryService.removeAssetById(assetId));
    }

    private Response<List<AssetDTO>> getAllAssetsHandler(Request request) {
        if (!(request instanceof GetAllAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        return Response.of(libraryService.getAllAssets());
    }

    private Response<List<AssetDTO>> getAssetsByTitleHandler(Request request) {
        if (!(request instanceof GetAssetsByTitleRequest getAssetsByTitleRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String title = getAssetsByTitleRequest.getTitle();
        return Response.of(libraryService.getAssetsByTitle(title));
    }

    private Response<List<AssetDTO>> getAllBorrowableAssetsHandler(Request request) {
        if (!(request instanceof GetAllBorrowableAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        return Response.of(libraryService.getAllBorrowableAssets());
    }

    private Response<List<AssetDTO>> getAssetsByTypeRequest(Request request) {
        if (!(request instanceof GetAssetsByTypeRequest getAssetsByTypeRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String type = getAssetsByTypeRequest.getAssetType();
        return Response.of(libraryService.getAssetsByType(type));
    }

    private Response<List<AssetDTO>> queryAssetsHandler(Request request) {
        if (!(request instanceof QueryAssetsRequest queryAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String query = queryAssetsRequest.getQuery();
        return Response.of(libraryService.queryAssets(query));
    }

    private Response<String> borrowAssetHandler(Request request) {
        if (!(request instanceof BorrowAssetRequest borrowAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = borrowAssetRequest.getAssetId();
        LocalDate returnDate = borrowAssetRequest.getReturnDate();
        return Response.of(libraryService.borrowAssetById(assetId, returnDate));
    }

    private Response<String> returnAssetHandler(Request request) {
        if (!(request instanceof ReturnAssetRequest returnAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = returnAssetRequest.getAssetId();
        return Response.of(libraryService.returnAssetById(assetId));
    }

    private Response<String> updateAssetHandler(Request request) {
        if (!(request instanceof UpdateAssetRequest updateAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = updateAssetRequest.getAssetId();
        Asset updatedAsset = updateAssetRequest.getUpdatedAsset();
        return Response.of(libraryService.updateAssetById(assetId, updatedAsset));
    }

    private Response<String> handleUnsupportedRequest(Request request) {
        return new Response<>("Request type not supported!");
    }

    private Response<AssetDTO> getAssetByIdHandler(Request request) {
        if (!(request instanceof GetAssetByIdRequest getAssetByIdRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = getAssetByIdRequest.getAssetId();
        return Response.of(libraryService.getAssetById(assetId));
    }

    private Response<String> subscribeToAssetHandler(Request request) {
        if (!(request instanceof SubscribeToAssetRequest subscribeToAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = subscribeToAssetRequest.getAssetId();
        var subscriber = subscribeToAssetRequest.getSubscriber();
        return Response.of(libraryService.subscribeToAssetById(assetId, subscriber));
    }
}
