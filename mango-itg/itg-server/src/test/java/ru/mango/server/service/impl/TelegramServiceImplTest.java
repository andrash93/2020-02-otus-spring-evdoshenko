package ru.mango.server.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mango.common.dto.telegram.TelegramMessageDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TelegramServiceImplTest {

    @InjectMocks
    private TelegramServiceImpl telegramService;
    @Test
    public void sendMessageTest() {
//        final String text = "";
//        final Long id = 123L;
//        telegramService.sendMessage(text,id);
//        TelegramMessageDto message = new TelegramMessageDto();
//        message.setChatId(id);
//        message.setText(text);
//        verify(telegramService, times(1)).sendMessage(message);
    }

}