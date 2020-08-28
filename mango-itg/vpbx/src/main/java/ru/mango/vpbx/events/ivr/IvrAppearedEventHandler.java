package ru.mango.vpbx.events.ivr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.service.CallService;

@MessageEndpoint
public class IvrAppearedEventHandler {

    private final Logger logger = LoggerFactory.getLogger(IvrAppearedEventHandler.class);

    private final CallService callService;

    public IvrAppearedEventHandler(CallService callService) {
        this.callService = callService;
    }

    @ServiceActivator(inputChannel = "callRouterIvrAppearedChannel")
    public void handle(CallEvent callEvent) {
        logger.info("entryId {} IvrAppeared: {}", callEvent.getCall().getEntryId(), callEvent);
        callService.updateCallState(callEvent.getVpbxApiKey(), (VpbxCallDto) callEvent.getCall());
    }

}
