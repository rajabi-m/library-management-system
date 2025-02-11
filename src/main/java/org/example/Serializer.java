package org.example;

public interface Serializer<T> {
    String serialize(T object);
    T deserialize(String data);
}
