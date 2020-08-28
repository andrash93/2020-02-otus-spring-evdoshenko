package ru.mango.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;
import ru.mango.server.events.telegram.TelegramMessageHandler;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    private final Logger logger = LoggerFactory.getLogger(TelegramController.class);
    private final TelegramMessageHandler telegramEventHandler;

    public TelegramController(TelegramMessageHandler telegramEventHandler) {
        this.telegramEventHandler = telegramEventHandler;
    }

    @PostMapping("/message")
    public String handleTelegramMessage(@RequestBody TelegramMessageEntryDto messageEntry) {
        logger.info("invoke handleTelegramMessage {}", messageEntry);
        telegramEventHandler.handle(MessageBuilder
                .withPayload(messageEntry)
                .build());
        return "success"; //todo понять яе возвращать
    }

}
