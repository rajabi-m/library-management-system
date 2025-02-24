package org.example.controller;

import org.example.service.requests.Request;
import org.example.service.response.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ConnectionBridge {
    private final static Logger logger = Logger.getLogger(ConnectionBridge.class.getSimpleName());
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response<?>> responseQueue = new LinkedBlockingQueue<>();

    public void addRequest(Request request) throws InterruptedException {
        requestQueue.put(request);
    }

    public Request takeRequest() throws InterruptedException {
        return requestQueue.take();
    }

    public void addResponse(Response<?> response) throws InterruptedException {
        responseQueue.put(response);
    }

    public Response<?> takeResponse() throws InterruptedException {
        return responseQueue.take();
    }

    public <T> Response<T> sendRequestAndWaitForResponse(Request request) {
        try {
            logger.info("Sending request: " + request);
            requestQueue.put(request);
            Response<?> response = responseQueue.take();
            logger.info("Received response: " + response);

            @SuppressWarnings("unchecked")
            var castedResponse = (Response<T>) response;
            return castedResponse;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Invalid response type: " + e.getMessage());
        }
    }
}
