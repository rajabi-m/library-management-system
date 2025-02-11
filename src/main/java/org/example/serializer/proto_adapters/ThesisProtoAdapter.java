package org.example.serializer.proto_adapters;

import com.example.model.proto.ProtoMessages;
import com.google.protobuf.GeneratedMessage;
import org.example.model.Asset;
import org.example.model.Thesis;

public class ThesisProtoAdapter implements ProtoAdapter<Asset> {
    @Override
    public ProtoMessages.Asset toProto(Asset asset) {
        if (!(asset instanceof Thesis thesis)) {
            throw new IllegalArgumentException("Invalid asset type");
        }
        return ProtoMessages.Asset.newBuilder().setThesis(ProtoMessages.Thesis.newBuilder()
                .setId(thesis.getId())
                .setTitle(thesis.getTitle())
                .setAuthor(thesis.getAuthor())
                .setSupervisor(thesis.getSupervisor())
                .setDepartment(thesis.getDepartment())
                .setPublishDate(thesis.getPublishDate())
                .build()).build();
    }

    @Override
    public Thesis fromProto(ProtoMessages.Asset proto) {
        ProtoMessages.Thesis thesisProto = proto.getThesis();
        return new Thesis(
                thesisProto.getId(),
                thesisProto.getTitle(),
                thesisProto.getAuthor(),
                thesisProto.getSupervisor(),
                thesisProto.getDepartment(),
                thesisProto.getPublishDate()
        );
    }
}
