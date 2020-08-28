package ru.mango.telegram.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mango.common.dto.telegram.TelegramMessageDto;
import ru.mango.common.dto.telegram.TelegramMessageEntryDto;
import ru.mango.telegram.bot.config.ServiceLocationConfig;


@Service
public class TelegramServiceImpl implements TelegramService {

    private Logger logger = LoggerFactory.getLogger(TelegramServiceImpl.class);

    private final TelegramBot telegramBot;
    private final RestTemplate restTemplate;
    private final ServiceLocationConfig serviceLocationConfig;

    private final String TELEGRAM_MESSAGE = "/telegram/message";

    public TelegramServiceImpl(TelegramBot telegramBot,
                               ServiceLocationConfig serviceLocationConfig) {
        this.telegramBot = telegramBot;
        this.serviceLocationConfig = serviceLocationConfig;
        restTemplate = new RestTemplate();
    }

    @EventListener
    @Override
    public void handleInboundMessage(Update update) {
        logger.info("invoke handleInboundMessage");
        Message message = update.message() != null ? update.message(): update.callbackQuery().message();

        TelegramMessageEntryDto telegramMessageEntry = getTelegramMessageEntry(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> body = new HttpEntity(telegramMessageEntry, headers);
        try {
            String url = serviceLocationConfig.getItgServerService() + TELEGRAM_MESSAGE;
            ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);

            if (response.getBody() == null) {
                logger.error("ERROR in {} response body = null", TELEGRAM_MESSAGE);
            }

            logger.info("handleInboundMessage response body = {}", response.getBody());
        } catch (Exception e) {
            logger.info("error in handleInboundMessage {}", e.getMessage());
        }
    }

    @Override
    public void sendMessage(TelegramMessageDto messageDto) {
        SendMessage message = new SendMessage(messageDto.getChatId(), messageDto.getText())
                .parseMode(ParseMode.HTML);//.replyMarkup(inlineKeyboard);
        SendResponse sendResponse = telegramBot.execute(message);
    }

    private TelegramMessageEntryDto getTelegramMessageEntry(Message message) {
        TelegramMessageEntryDto telegramMessageEntry = new TelegramMessageEntryDto();
        telegramMessageEntry.setUserId(message.from().id());
        telegramMessageEntry.setChatId(message.chat().id());
        telegramMessageEntry.setFirstName(message.from().firstName());
        telegramMessageEntry.setUserName(message.from().username());
        telegramMessageEntry.setText(message.text());
        if (message.leftChatMember() != null) {
            telegramMessageEntry.setLeftChat(true);
        }
        if (message.newChatMembers() != null) {
            telegramMessageEntry.setAddToChat(true);
        }
        telegramMessageEntry.setChatType(message.chat().type().name());

        return telegramMessageEntry;
    }

}
