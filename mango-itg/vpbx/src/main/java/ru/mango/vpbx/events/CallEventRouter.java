package ru.mango.vpbx.events;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import ru.mango.common.model.vpbx.CallLocation;
import ru.mango.common.model.vpbx.CallState;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;


@MessageEndpoint
public class CallEventRouter {

    @Router(inputChannel = "callEventFilterOutputChannel")
    public String routeCall(CallEvent call) {
        if (call.getCall() instanceof VpbxSummaryDto) {
            return "summaryRouterChannel";
        }
        if (((VpbxCallDto) call.getCall()).getLocation() == CallLocation.ivr && ((VpbxCallDto) call.getCall()).getCallState() == CallState.Appeared) {
            return "callRouterIvrAppearedChannel";
        }
        if (((VpbxCallDto) call.getCall()).getLocation() == CallLocation.ivr && ((VpbxCallDto) call.getCall()).getCallState() == CallState.Disconnected) {
            return "callRouterIvrDisconnectedChannel";
        }
        if (((VpbxCallDto) call.getCall()).getLocation() == CallLocation.abonent && ((VpbxCallDto) call.getCall()).getCallState() == CallState.Appeared) {
            return "callRouterAbonentAppearedChannel";
        }
        if (((VpbxCallDto) call.getCall()).getLocation() == CallLocation.abonent && ((VpbxCallDto) call.getCall()).getCallState() == CallState.Connected) {
            return "callRouterAbonentConnectedChannel";
        }
        if (((VpbxCallDto) call.getCall()).getLocation() == CallLocation.abonent && ((VpbxCallDto) call.getCall()).getCallState() == CallState.Disconnected) {
            return "callRouterAbonentDisconnectedChannel";
        }

        return "nullChannel";
    }

}
