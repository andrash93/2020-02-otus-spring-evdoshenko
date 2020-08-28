package ru.mango.telegram.bot.events;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateEventListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(UpdateEventListener.class);
    private final ApplicationEventPublisher publisher;

    public UpdateEventListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            try {
                publisher.publishEvent(update);
            } catch (Exception e) {
                logger.error("error in process UpdateEventListener", e);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
