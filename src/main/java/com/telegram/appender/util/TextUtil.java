package com.telegram.appender.util;

import java.util.Objects;

public class TextUtil {
    public static String configureSize(String text, int maxSize) {
        StringBuilder result = new StringBuilder();
        if (Objects.nonNull(text) && text.length() >= maxSize) {
            result.append(text, 0, maxSize-8).append(".....");
            return result.toString();
        }
        return text;
    }
}
