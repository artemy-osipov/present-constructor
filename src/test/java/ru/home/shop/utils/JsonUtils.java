package ru.home.shop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static String toJson(Object o) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(o);
    }

    public static <T> T fromJson(String json, Class<T> klass) throws IOException {
        return JSON_MAPPER.readValue(json, klass);
    }
}
