package org.example.service;

import org.example.model.Asset;
import org.example.model.Library;
import org.example.model.dto.AssetDTO;
import org.example.service.requests.*;
import org.example.service.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class LibraryManagementService implements Runnable {
    private final Library library;
    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Response<?>> responseQueue;

    private final Map<Class<? extends Request>, Function<Request, Response<?>>> requestHandlers = Map.of(
            AddAssetRequest.class, this::addAssetHandler,
            RemoveAssetRequest.class, this::removeAssetHandler,
            GetAllAssetsRequest.class, this::getAllAssetsHandler,
            GetAssetsByTitleRequest.class, this::getAssetsByTitleHandler,
            GetAllBorrowableAssetsRequest.class, this::getAllBorrowableAssetsHandler,
            GetAssetsByTypeRequest.class, this::getAssetsByTypeRequest,
            QueryAssetsRequest.class, this::queryAssetsHandler,
            BorrowAssetRequest.class, this::borrowAssetHandler,
            ReturnAssetRequest.class, this::returnAssetHandler
    );

    public LibraryManagementService(Library library, BlockingQueue<Request> requestQueue, BlockingQueue<Response<?>> responseQueue) {
        this.library = library;
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Request request = requestQueue.take();
                if (!requestHandlers.containsKey(request.getClass())){
                    responseQueue.put(new Response<>("Request type not supported!"));
                }
                var handler = requestHandlers.get(request.getClass());
                responseQueue.put(handler.apply(request));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Response<String> addAssetHandler(Request request) {
        if (!(request instanceof AddAssetRequest addAssetRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        Asset asset = addAssetRequest.getAsset();
        return new Response<>(library.addAsset(asset));
    }

    private Response<String> removeAssetHandler(Request request){
        if (!(request instanceof RemoveAssetRequest removeAssetRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = removeAssetRequest.getAssetId();
        return new Response<>(library.removeAssetById(assetId));
    }

    private Response<ArrayList<AssetDTO>> getAllAssetsHandler(Request request){
        if (!(request instanceof GetAllAssetsRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        return new Response<>(library.getAllAssets());
    }

    private Response<ArrayList<AssetDTO>> getAssetsByTitleHandler(Request request){
        if (!(request instanceof GetAssetsByTitleRequest getAssetsByTitleRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String title = getAssetsByTitleRequest.getTitle();
        return new Response<>(library.getAssetsByTitle(title));
    }

    private Response<ArrayList<AssetDTO>> getAllBorrowableAssetsHandler(Request request){
        if (!(request instanceof GetAllBorrowableAssetsRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        return new Response<>(library.getAllBorrowableAssets());
    }

    private Response<ArrayList<AssetDTO>> getAssetsByTypeRequest (Request request){
        if (!(request instanceof GetAssetsByTypeRequest getAssetsByTypeRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String type = getAssetsByTypeRequest.getAssetType();
        return new Response<>(library.getAssetsByType(type));
    }

    private Response<ArrayList<AssetDTO>> queryAssetsHandler(Request request){
        if (!(request instanceof QueryAssetsRequest queryAssetsRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String query = queryAssetsRequest.getQuery();
        return new Response<>(library.queryAssets(query));
    }

    private Response<String> borrowAssetHandler(Request request){
        if (!(request instanceof BorrowAssetRequest borrowAssetRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = borrowAssetRequest.getAssetId();
        LocalDate returnDate = borrowAssetRequest.getReturnDate();
        return new Response<>(library.borrowAssetById(assetId, returnDate));
    }

    private Response<String> returnAssetHandler(Request request){
        if (!(request instanceof ReturnAssetRequest returnAssetRequest)){
            throw new RuntimeException("Invalid request type!");
        }

        String assetId = returnAssetRequest.getAssetId();
        return new Response<>(library.returnAssetById(assetId));
    }



}
