package com.geprice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorConfig {

    @ExceptionHandler(GEPrice404Error.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, Object> handle404(GEPrice404Error message) {
        Map<String, Object> response =  new HashMap<>();
        response.put("error", message.getMessage());
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handle400() {
        Map<String, Object> response =  new HashMap<>();
        response.put("error", "Invalid request");
        return response;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handle(NoHandlerFoundException message) {
        Map<String, Object> response =  new HashMap<>();
        response.put("error", message.getMessage());
        return response;
    }
}
