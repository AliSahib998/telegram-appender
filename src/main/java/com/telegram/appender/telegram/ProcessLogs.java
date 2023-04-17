package com.telegram.appender.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessLogs {

    private final TelegramBot telegramBot;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final BlockingQueue<AppenderParameters> logQueue = new LinkedBlockingQueue<>(100);

    public ProcessLogs(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void putLogs(AppenderParameters appenderParameters) throws InterruptedException {
        logQueue.put(appenderParameters);
        executor.submit(this::processLogs);
    }

    public void processLogs()  {
        while (!logQueue.isEmpty()) {
            AppenderParameters log = logQueue.poll();
            try {
                telegramBot.sendMessage(log);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
