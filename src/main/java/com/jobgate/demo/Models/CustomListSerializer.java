package com.jobgate.demo.Models;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jobgate.demo.Models.SubCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializer extends StdSerializer<List<SubCategory>> {

    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(Class<List<SubCategory>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<SubCategory> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<SubCategory> ids = new ArrayList<>();
        for (SubCategory item : items) {
            ids.add(item);
        }
        generator.writeObject(ids);
    }
}