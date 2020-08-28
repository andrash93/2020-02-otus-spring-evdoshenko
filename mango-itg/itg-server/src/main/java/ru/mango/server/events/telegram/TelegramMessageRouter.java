package ru.mango.server.events.telegram;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;

@MessageEndpoint
public class TelegramMessageRouter {

    @Router(inputChannel = "telegramMessageChannel")
    public String routeMessage(TelegramMessageEntryDto message) {
        if ("Private".equals(message.getChatType())) {
            return "privateMessageRouterChannel";
        }
        if ("group".equals(message.getChatType())) {
            return "groupMessageRouterChannel";
        }
        return "nullChannel";
    }
}
