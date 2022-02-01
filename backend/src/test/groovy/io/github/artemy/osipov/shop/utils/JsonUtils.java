package io.github.artemy.osipov.shop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static String toJson(Object o) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(o);
    }
}
