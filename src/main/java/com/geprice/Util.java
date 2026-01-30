package com.geprice;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.List;

public class Util {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static String toJson(Object o) {
        return MAPPER.writeValueAsString(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }

    public static <T> T fromObjectNode(ObjectNode node, Class<T> clazz) {
        return MAPPER.convertValue(node, clazz);
    }

    public static <T> List<T> listFromFile(File file, Class<T> clazz) {
        return MAPPER.readValue(file, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
