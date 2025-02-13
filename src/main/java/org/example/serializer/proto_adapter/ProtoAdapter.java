package org.example.serializer.proto_adapter;

import com.example.model.proto.ProtoMessages;

public interface ProtoAdapter<T> {
    ProtoMessages.Asset toProto(T object);
    T fromProto(ProtoMessages.Asset proto);
}
