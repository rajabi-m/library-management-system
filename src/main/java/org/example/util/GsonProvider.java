package org.example.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.Asset;
import org.example.model.Book;
import org.example.model.Magazine;
import org.example.model.Thesis;
import org.example.util.type_adapters.LocalDateTypeAdapter;
import org.example.util.type_adapters.RuntimeTypeAdapterFactory;

import java.time.LocalDate;

public class GsonProvider {
    private static final RuntimeTypeAdapterFactory<Asset> adapterFactory = RuntimeTypeAdapterFactory
            .of(Asset.class, "type", true)
            .registerSubtype(Book.class, Book.class.getSimpleName())
            .registerSubtype(Thesis.class, Thesis.class.getSimpleName())
            .registerSubtype(Magazine.class, Magazine.class.getSimpleName());

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(adapterFactory)
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .setPrettyPrinting()
            .create();

    public static Gson getGson() {
        return gson;
    }
}
