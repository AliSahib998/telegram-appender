package com.telegram.appender.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.appender.enums.HttpMethods;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;


public class HttpClientConfiguration {
    private final int connectionTimeout;
    private final int requestTimeout;

    public HttpClientConfiguration(int connectionTimeout, int requestTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.requestTimeout = requestTimeout;
    }

    public HttpClient getHttpRequest() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    public HttpRequest buildHttpRequest(String uri, HttpMethods method,
                                        Map<String, String> bodyParams) throws JsonProcessingException {
        if (Objects.nonNull(method) && method.equals(HttpMethods.POST)) {
            String requestBody = null;
            if (Objects.nonNull(bodyParams)) {
                ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
                requestBody = objectMapper
                        .writeValueAsString(bodyParams);
            }
            return HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .timeout(Duration.ofSeconds(requestTimeout))
                    .header("Content-Type", "application/json")
                    .POST(Objects.isNull(requestBody) ? HttpRequest.BodyPublishers.noBody() :
                            HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
        }
        //TODO add other methods
        return null;
    }
}
