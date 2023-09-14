package com.mjc.school.common.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static PropertyLoader propertyLoader = PropertyLoader.getInstance();

    private JsonUtils(){}

    public static <T> List<T> deserializeList(String json, Class<T> innerType ) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        objectMapper.configure( DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false );

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        JavaType type = typeFactory.constructParametricType( ArrayList.class, innerType );
        return objectMapper.readValue(json, type);
    }

    public static <T> T deserialize(String json, Class<T> modelType ) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        objectMapper.configure( DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false );

        return objectMapper.readValue(json, modelType);
    }

    public static <T> String serialize(T source) throws JacksonException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure( SerializationFeature.WRITE_NULL_MAP_VALUES, false );

        String isoformat = "yyyy-MM-dd'T'HH:mm";
        objectMapper.configOverride(LocalDateTime.class).setFormat(JsonFormat.Value.forPattern(isoformat));

        return objectMapper.writeValueAsString(source);
    }
}
