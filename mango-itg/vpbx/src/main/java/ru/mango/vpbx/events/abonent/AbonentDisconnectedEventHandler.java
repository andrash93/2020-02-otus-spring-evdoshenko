package ru.mango.vpbx.events.abonent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.service.CallService;

@MessageEndpoint
public class AbonentDisconnectedEventHandler {

    private final Logger logger = LoggerFactory.getLogger(AbonentDisconnectedEventHandler.class);

    private final CallService callService;

    public AbonentDisconnectedEventHandler(CallService callService) {
        this.callService = callService;
    }

    @ServiceActivator(inputChannel = "callRouterAbonentDisconnectedChannel")
    public void handle(CallEvent call) {
        logger.info("entryId {} AbonentDisconnected: {}", call.getCall().getEntryId(), call);
        callService.updateCallState(call.getVpbxApiKey(), (VpbxCallDto) call.getCall());
    }

}
