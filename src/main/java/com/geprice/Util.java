package com.geprice;

import com.geprice.error.GEPrice404Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static void logMissingSubmission(String id) {
        log.warn("Submission with id {} not found", id);
    }

    public static int validateIntegerParameter(String integer, String failMessage) {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            throw new GEPrice404Error(failMessage);
        }
    }
}
