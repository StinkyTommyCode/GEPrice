package com.geprice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

public class Util {
    private static final ObjectMapper MAPPER= new ObjectMapper();
    public static final Logger log = LoggerFactory.getLogger(Util.class);

    public static String toJson(Object o) {
        return MAPPER.writeValueAsString(o);
    }

    public static void logMissingSubmission(String id) {
        log.warn("Submission with id {} not found", id);
    }
}
