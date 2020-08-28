package ru.mango.telegram.bot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mango.telegram.bot.events.UpdateEventListener;

@Configuration
public class SpringConfig {

    @Bean
    public TelegramBot telegramBot(TelegramBotProperties telegramBotProperties, UpdateEventListener updateEventListener) {
        TelegramBot telegramBot = new TelegramBot(telegramBotProperties.getToken());
        telegramBot.setUpdatesListener(updateEventListener);
        return telegramBot;
    }
}
