package ru.mango.vpbx.events;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import ru.mango.vpbx.dto.CallEvent;


@MessagingGateway(name = "callEventHandler",
        defaultRequestChannel = "callEventChannel")
public interface CallEventHandler {

    @Gateway
    void handle(Message<CallEvent> callEvent);

}
