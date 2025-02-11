package org.example.serializer.proto_adapters;

import com.example.model.proto.ProtoMessages;
import com.google.protobuf.GeneratedMessage;
import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.Book;

import java.time.LocalDate;

public class BookProtoAdapter implements ProtoAdapter<Asset> {
    @Override
    public ProtoMessages.Asset toProto(Asset asset) {
        if (!(asset instanceof Book book)) {
            throw new IllegalArgumentException("Invalid asset type");
        }
        return ProtoMessages.Asset.newBuilder().setBook(ProtoMessages.Book.newBuilder()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setStatus(ProtoMessages.AssetStatus.valueOf(book.getStatus().name()))
                .setReleaseYear(book.getReleaseYear())
                .setReturnDate(book.getReturnDate().toEpochDay())
                .build()).build();
    }

    @Override
    public Book fromProto(ProtoMessages.Asset asset) {
        return new Book(
                (asset.getBook()).getId(),
                (asset.getBook()).getTitle(),
                (asset.getBook()).getAuthor(),
                (asset.getBook()).getReleaseYear(),
                AssetStatus.valueOf((asset.getBook()).getStatus().name()),
                LocalDate.ofEpochDay((asset.getBook()).getReturnDate())
        );
    }
}
