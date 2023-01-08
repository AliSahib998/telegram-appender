package com.telegram.appender.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    private static ObjectMapper objectMapper = null;

    private ObjectMapperSingleton() {

    }

    public static ObjectMapper getInstance()
    {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();

        return objectMapper;
    }
}
