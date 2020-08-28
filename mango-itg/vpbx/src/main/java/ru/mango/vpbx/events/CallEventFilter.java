package ru.mango.vpbx.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;

import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;

@MessageEndpoint
public class CallEventFilter {

    private final Logger logger = LoggerFactory.getLogger(CallEventFilter.class);

    @Filter(inputChannel = "callEventChannel", outputChannel = "callEventFilterOutputChannel")
    public boolean filterInternalCall(CallEvent call) {
        if (call.getCall() instanceof VpbxSummaryDto) {
            return true;
        }
        return ((VpbxCallDto) call.getCall()).getCallDirection() != CallDirection.internal;
    }
}
