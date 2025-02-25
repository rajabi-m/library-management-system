package org.example.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.requests.Request;
import org.example.service.response.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionBridge {
    private final static Logger logger = LogManager.getLogger(ConnectionBridge.class.getSimpleName());
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
            Response<?> response = responseQueue.poll(1, TimeUnit.SECONDS);
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
