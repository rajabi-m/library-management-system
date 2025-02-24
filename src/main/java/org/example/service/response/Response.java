package org.example.service.response;

public record Response<T>(T data) {
    static public <T> Response<T> of(T data) {
        return new Response<>(data);
    }
}