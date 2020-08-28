package ru.mango.server.events.telegram;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;

@MessagingGateway(name = "telegramMessageHandler",
        defaultRequestChannel = "telegramMessageChannel")
public interface TelegramMessageHandler {

    @Gateway
    void handle(Message<TelegramMessageEntryDto> message);

}
