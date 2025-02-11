package org.example.serializer.proto_adapters;

import com.example.model.proto.ProtoMessages;
import com.google.protobuf.GeneratedMessage;

public interface ProtoAdapter<T> {
    ProtoMessages.Asset toProto(T object);
    T fromProto(ProtoMessages.Asset proto);
}
