package ru.mango.telegram.bot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mango.common.dto.telegram.TelegramMessageDto;
import ru.mango.telegram.bot.service.TelegramService;

@RestController
@RequestMapping("/telegram")
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final TelegramService telegramService;

    public MessageController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/message")
    public String handleMessage(@RequestBody TelegramMessageDto message) {
        logger.info("invoke handleMessage {}", message);
        telegramService.sendMessage(message);
        return "success"; //todo понять яе возвращать
    }
}
