package ru.mango.server.service;

public interface TelegramService {

    void sendMessage(Long chatId, String text);

}
