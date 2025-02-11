package org.example.serializer;

public interface Serializer<T> {
    byte[] serialize(T object);
    T deserialize(byte[] data);
}
