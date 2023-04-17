# telegram-appender
This is a simple library that sends critical logs to Telegram based on a specific pattern.

# Why we need to it
It can be used for alerting by sending critical errors in JSON format to Telegram channels, making it easy to search and identify the root cause.

# Download
```groovy
dependencies {
    implementation 'com.telegram.appender:telegram-logback-appender:1.0.0'
}
```
Repository setting:
```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/AliSahib998/telegram-appender")
    }
}
```

# Example logback.xml
```xml
<configuration debug="true">

        <appender name="TELEGRAM"
class="com.telegram.appender.telegram.TelegramAppender">
<botToken>YOUR TOKEN</botToken>
        <chatId>YOUR CHAT ID</chatId>
<Layout class="ch.qos.logback.classic.PatternLayout">
<Pattern>{ "level": "%level" , "app_name" : "telegram-appender",
"thread": "%thread", "message": "%message %ex" }</Pattern>
        </Layout>
<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
<level>ERROR</level>
        </filter>
</appender>
    
    
    <root level="info">
        <appender-ref ref="TELEGRAM"/>
</root>
</configuration>
```