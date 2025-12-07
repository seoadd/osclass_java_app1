package com.example.osclassapp.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DirectArrayAdapter<T> extends TypeAdapter<List<T>> {
    private final Class<T> adapterclass;

    public DirectArrayAdapter(Class<T> adapterclass) {
        this.adapterclass = adapterclass;
    }

    public List<T> read(JsonReader reader) throws IOException {
        List<T> list = new ArrayList<T>();
        Gson gson = new Gson();

        if (reader.peek() == null) {
            reader.nextNull();
            return list;
        }

        if (reader.peek() == com.google.gson.stream.JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            while (reader.hasNext()) {
                T inning = gson.fromJson(reader, adapterclass);
                list.add(inning);
            }
            reader.endArray();
        } else {
            T single = gson.fromJson(reader, adapterclass);
            list.add(single);
        }

        return list;
    }

    @Override
    public void write(JsonWriter writer, List<T> value) throws IOException {
        // Not needed for reading
    }
}