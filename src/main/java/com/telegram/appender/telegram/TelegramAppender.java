package com.telegram.appender.telegram;

import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.status.ErrorStatus;
import com.telegram.appender.telegram.impl.TelegramBotImpl;

import java.util.Objects;

public class TelegramAppender<E> extends UnsynchronizedAppenderBase<E> {

    private String chatId;
    private String botToken;
    private int connectionTimeout = 60;
    private int requestTimeout = 60;
    private String parseMode = null;
    protected Layout<E> layout;

    public Layout<E> getLayout() {
        return layout;
    }

    public void setLayout(Layout<E> layout) {
        this.layout = layout;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        try {
           this.connectionTimeout = Integer.parseInt(connectionTimeout);
        } catch (NumberFormatException ex) {
            addStatus(new ErrorStatus("connection timeout should be integer format", this));
        }
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(String requestTimeout) {
        try {
            this.requestTimeout = Integer.parseInt(requestTimeout);
        } catch (NumberFormatException ex) {
            addStatus(new ErrorStatus("connection request timeout should be integer format", this));
        }
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    @Override
    public void start() {
        boolean hasError = false;
        if (Objects.isNull(this.chatId)) {
            addStatus(new ErrorStatus("chat id is null", this));
            hasError = true;
        }
        if (Objects.isNull(this.botToken)) {
            addStatus(new ErrorStatus("bot token is null", this));
            hasError = true;
        }
        if (Objects.isNull(this.layout)) {
            addStatus(new ErrorStatus("bot token is null", this));
            hasError = true;
        }
        if (!hasError) {
            super.start();
        }
    }

    @Override
    protected void append(E eventObject) {
        if (!isStarted()) {
            return;
        }
        String text = layout.doLayout(eventObject);
        AppenderParameters appenderParameters = AppenderParameters.builder()
                .botToken(this.botToken)
                .chatId(this.chatId)
                .connectionRequestTimeout(this.requestTimeout)
                .connectionTimeout(this.connectionTimeout)
                .parseMode(this.parseMode)
                .text(text)
                .build();
        new Thread(new TelegramBotImpl(appenderParameters)).start();
    }
}
