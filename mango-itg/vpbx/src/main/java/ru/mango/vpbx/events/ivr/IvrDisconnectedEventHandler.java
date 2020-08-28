package ru.mango.vpbx.events.ivr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.service.CallService;

@MessageEndpoint
public class IvrDisconnectedEventHandler {

    private final Logger logger = LoggerFactory.getLogger(IvrDisconnectedEventHandler.class);

    private final CallService callService;

    public IvrDisconnectedEventHandler(CallService callService) {
        this.callService = callService;
    }

    @ServiceActivator(inputChannel = "callRouterIvrDisconnectedChannel")
    public void handle(CallEvent call) {
        logger.info("entryId {} IvrDisconnected: {}", call.getCall().getEntryId(), call);
        callService.updateCallState(call.getVpbxApiKey(), (VpbxCallDto) call.getCall());
    }

}
