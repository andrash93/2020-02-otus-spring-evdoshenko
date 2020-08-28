package ru.mango.vpbx.events.abonent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.service.CallService;

@MessageEndpoint
public class AbonentAppearedEventHandler {

    private final Logger logger = LoggerFactory.getLogger(AbonentAppearedEventHandler.class);

    private final CallService callService;

    public AbonentAppearedEventHandler(CallService callService) {
        this.callService = callService;
    }

    @ServiceActivator(inputChannel = "callRouterAbonentAppearedChannel")
    public void handle(CallEvent callEvent) {
        logger.info("entryId {} AbonentAppeared: {}", callEvent.getCall().getEntryId(), callEvent);
        callService.updateCallState(callEvent.getVpbxApiKey(), (VpbxCallDto) callEvent.getCall());
    }

}
