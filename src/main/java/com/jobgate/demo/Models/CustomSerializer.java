package com.jobgate.demo.Models;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jobgate.demo.Models.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomSerializer extends StdSerializer<Category> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Category> t) {
        super(t);
    }

    @Override
    public void serialize(
            Category item,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {
        Category category=new Category();
        category.setName(item.getName());
        category.setId(item.getId());
        category.setDescription(item.getDescription());
        // category.setSubCategories(item.getSubCategories());
        generator.writeObject(category);
    }
}