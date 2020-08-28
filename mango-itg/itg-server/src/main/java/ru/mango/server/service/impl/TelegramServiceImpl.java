package ru.mango.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mango.common.dto.telegram.TelegramMessageDto;
import ru.mango.server.config.ServiceLocationConfig;
import ru.mango.server.service.TelegramService;

@Service
public class TelegramServiceImpl implements TelegramService {

    private final Logger logger = LoggerFactory.getLogger(TelegramServiceImpl.class);
    private final String TELEGRAM_MESSAGE = "/telegram/message";

    private final RestTemplate restTemplate;
    private final ServiceLocationConfig serviceLocationConfig;

    public TelegramServiceImpl(ServiceLocationConfig serviceLocationConfig) {
        restTemplate = new RestTemplate();
        this.serviceLocationConfig = serviceLocationConfig;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        TelegramMessageDto message = new TelegramMessageDto();
        message.setChatId(chatId);
        message.setText(text);
        sendMessage(message);
    }

    private void sendMessage(TelegramMessageDto message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> body = new HttpEntity(message, headers);
        try {
            String url = serviceLocationConfig.getTelegramBotService() + TELEGRAM_MESSAGE;
            ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);

            if (response.getBody() == null) {
                logger.error("ERROR in {} response body = null", TELEGRAM_MESSAGE);
            }

            logger.info("handleInboundMessage response body = {}", response.getBody());
        } catch (Exception e) {
            logger.info("error in handleInboundMessage {}", e.getMessage());
        }
    }
}
