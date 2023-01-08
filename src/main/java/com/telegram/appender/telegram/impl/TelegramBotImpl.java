package com.telegram.appender.telegram.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telegram.appender.config.HttpClientConfiguration;
import com.telegram.appender.enums.HttpMethods;
import com.telegram.appender.telegram.AppenderParameters;
import com.telegram.appender.telegram.TelegramBot;
import lombok.SneakyThrows;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static com.telegram.appender.telegram.TelegramConstants.TELEGRAM_SEND_MESSAGE_URL;

public class TelegramBotImpl implements TelegramBot, Runnable {

    private final AppenderParameters appenderParameters;

    public TelegramBotImpl(AppenderParameters appenderParameters) {
        this.appenderParameters = appenderParameters;
    }

    @Override
    public void sendMessage(AppenderParameters appenderParameters) throws JsonProcessingException {
        String url = String.format(TELEGRAM_SEND_MESSAGE_URL, appenderParameters.getBotToken());
        HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration(appenderParameters.getConnectionTimeout(),
                appenderParameters.getConnectionRequestTimeout());
        HttpClient httpClient = httpClientConfiguration.getHttpRequest();
        Map<String, String> params = new HashMap<>();
        params.put("chat_id", appenderParameters.getBotToken());
        params.put("text", appenderParameters.getText());
        HttpRequest httpRequest = httpClientConfiguration.buildHttpRequest(url, HttpMethods.POST, params);
        try {
            httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        sendMessage(this.appenderParameters);
    }
}
