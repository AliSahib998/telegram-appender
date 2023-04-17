package com.telegram.appender.telegram.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.telegram.appender.config.HttpClientConfiguration;
import com.telegram.appender.telegram.AppenderParameters;
import com.telegram.appender.telegram.TelegramBot;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.telegram.appender.telegram.TelegramConstants.TELEGRAM_SEND_MESSAGE_URL;

public class TelegramBotImpl implements TelegramBot {

    @Override
    public void sendMessage(AppenderParameters appenderParameters) throws JsonProcessingException {
        String url = String.format(TELEGRAM_SEND_MESSAGE_URL, appenderParameters.getBotToken());
        HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration(appenderParameters.getConnectionTimeout(),
                appenderParameters.getConnectionRequestTimeout());
        HttpClient httpClient = httpClientConfiguration.getHttpRequest();
        Map<String, String> params = new HashMap<>();
        params.put("chat_id", appenderParameters.getChatId());
        params.put("text", appenderParameters.getText());
        if (appenderParameters.getParseMode() != null) {
            params.put("parse_mode", appenderParameters.getParseMode());
        }
        HttpRequest httpRequest = httpClientConfiguration.buildHttpRequest(url, "POST", params);
        try {
            if (Objects.nonNull(httpRequest)) {
                 httpClient.send(httpRequest,
                        HttpResponse.BodyHandlers.ofString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
