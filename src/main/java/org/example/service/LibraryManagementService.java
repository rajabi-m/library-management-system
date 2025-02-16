package org.example.service;

import org.example.model.Asset;
import org.example.model.Library;
import org.example.model.dto.AssetDTO;
import org.example.service.requests.*;
import org.example.service.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.logging.Logger;

public class LibraryManagementService implements Runnable {
    private final Library library;
    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Response<?>> responseQueue;
    private boolean serviceRunning = true;

    private final static Logger logger = Logger.getLogger(LibraryManagementService.class.getSimpleName());

    private final Map<Class<? extends Request>, Function<Request, Response<?>>> requestHandlers = new HashMap<>();

    public LibraryManagementService(Library library, BlockingQueue<Request> requestQueue, BlockingQueue<Response<?>> responseQueue) {
        this.library = library;
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
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
                Request request = requestQueue.take();
                logger.info("Received request: " + request);

                Function<Request, Response<?>> handler = requestHandlers.getOrDefault(
                        request.getClass(),
                        this::handleUnsupportedRequest
                );

                Response<?> response = handler.apply(request);
                logger.info("Sending response: " + response);
                responseQueue.put(response);
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
        return new Response<>(library.addAsset(asset));
    }

    private Response<String> removeAssetHandler(Request request) {
        if (!(request instanceof RemoveAssetRequest removeAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = removeAssetRequest.getAssetId();
        return new Response<>(library.removeAssetById(assetId));
    }

    private Response<ArrayList<AssetDTO>> getAllAssetsHandler(Request request) {
        if (!(request instanceof GetAllAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        return new Response<>(library.getAllAssets());
    }

    private Response<ArrayList<AssetDTO>> getAssetsByTitleHandler(Request request) {
        if (!(request instanceof GetAssetsByTitleRequest getAssetsByTitleRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String title = getAssetsByTitleRequest.getTitle();
        return new Response<>(library.getAssetsByTitle(title));
    }

    private Response<ArrayList<AssetDTO>> getAllBorrowableAssetsHandler(Request request) {
        if (!(request instanceof GetAllBorrowableAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        return new Response<>(library.getAllBorrowableAssets());
    }

    private Response<ArrayList<AssetDTO>> getAssetsByTypeRequest(Request request) {
        if (!(request instanceof GetAssetsByTypeRequest getAssetsByTypeRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String type = getAssetsByTypeRequest.getAssetType();
        return new Response<>(library.getAssetsByType(type));
    }

    private Response<ArrayList<AssetDTO>> queryAssetsHandler(Request request) {
        if (!(request instanceof QueryAssetsRequest queryAssetsRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String query = queryAssetsRequest.getQuery();
        return new Response<>(library.queryAssets(query));
    }

    private Response<String> borrowAssetHandler(Request request) {
        if (!(request instanceof BorrowAssetRequest borrowAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = borrowAssetRequest.getAssetId();
        LocalDate returnDate = borrowAssetRequest.getReturnDate();
        return new Response<>(library.borrowAssetById(assetId, returnDate));
    }

    private Response<String> returnAssetHandler(Request request) {
        if (!(request instanceof ReturnAssetRequest returnAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = returnAssetRequest.getAssetId();
        return new Response<>(library.returnAssetById(assetId));
    }

    private Response<String> updateAssetHandler(Request request) {
        if (!(request instanceof UpdateAssetRequest updateAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = updateAssetRequest.getAssetId();
        Asset asset = updateAssetRequest.getUpdatedAsset();
        return new Response<>(library.updateAssetByAssetId(assetId, asset));
    }

    private Response<String> handleUnsupportedRequest(Request request) {
        return new Response<>("Request type not supported!");
    }

    private Response<AssetDTO> getAssetByIdHandler(Request request) {
        if (!(request instanceof GetAssetByIdRequest getAssetByIdRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = getAssetByIdRequest.getAssetId();
        return new Response<>(library.getAssetById(assetId));
    }

    private Response<String> subscribeToAssetHandler(Request request) {
        if (!(request instanceof SubscribeToAssetRequest subscribeToAssetRequest)) {
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = subscribeToAssetRequest.getAssetId();
        var subscriber = subscribeToAssetRequest.getSubscriber();
        return new Response<>(library.subscribeToAsset(assetId, subscriber));
    }
}
