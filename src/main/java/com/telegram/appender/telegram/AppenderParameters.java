package com.telegram.appender.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppenderParameters {
    private String chatId;
    private String parseMode;
    private String text;
    private String botToken;
    private int connectionTimeout;
    private int connectionRequestTimeout;
}
