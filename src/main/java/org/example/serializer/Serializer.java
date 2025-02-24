package org.example.serializer;

import org.example.exception.InvalidAssetFileFormatException;

public interface Serializer<T> {
    byte[] serialize(T object);

    T deserialize(byte[] data) throws InvalidAssetFileFormatException;
}
