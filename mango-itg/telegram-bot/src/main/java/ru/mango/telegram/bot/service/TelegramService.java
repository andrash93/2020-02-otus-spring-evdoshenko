package ru.mango.telegram.bot.service;


import com.pengrad.telegrambot.model.Update;
import ru.mango.common.dto.telegram.TelegramMessageDto;

public interface TelegramService {

    void handleInboundMessage(Update update);

    void sendMessage(TelegramMessageDto message);
}
