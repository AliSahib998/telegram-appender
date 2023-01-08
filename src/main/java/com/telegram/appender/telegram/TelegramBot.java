package com.telegram.appender.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TelegramBot {
    void sendMessage(AppenderParameters appenderParameters) throws JsonProcessingException;
}
