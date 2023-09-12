package com.mjc.school.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonUtils {
    private static PropertyLoader propertyLoader = PropertyLoader.getInstance();

    private JsonUtils(){}

    public static <T> List<T> deserialize(String json) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<List<T>>() {});
    }

    public static <T> String serialize(T source) throws JacksonException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(source);
    }
}
