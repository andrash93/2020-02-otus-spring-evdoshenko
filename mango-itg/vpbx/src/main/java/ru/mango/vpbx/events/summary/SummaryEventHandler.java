package ru.mango.vpbx.events.summary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.mango.vpbx.dto.CallEvent;

import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;
import ru.mango.vpbx.service.CallService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@MessageEndpoint
public class SummaryEventHandler {

    private final Logger logger = LoggerFactory.getLogger(SummaryEventHandler.class);

    private final CallService callService;
    private final ScheduledExecutorService scheduledExecutorService;

    public SummaryEventHandler(CallService callService, ScheduledExecutorService scheduledExecutorService) {
        this.callService = callService;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @ServiceActivator(inputChannel = "summaryRouterChannel")
    public void handle(CallEvent call) {
        logger.info("entryId {} summaryRouterChannel: {}", call.getCall().getEntryId(), call);
        scheduledExecutorService.schedule(() -> {
            callService.finishCall(call.getVpbxApiKey(), (VpbxSummaryDto) call.getCall());
        }, 2, TimeUnit.SECONDS);
    }

}
