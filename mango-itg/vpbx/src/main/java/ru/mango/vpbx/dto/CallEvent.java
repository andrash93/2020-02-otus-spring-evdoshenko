package ru.mango.vpbx.dto;

import ru.mango.vpbx.dto.vpbx.VpbxCall;

public class CallEvent {

    private final VpbxCall call;
    private final String vpbxApiKey;

    public CallEvent(String vpbxApiKey, VpbxCall call) {
        this.call = call;
        this.vpbxApiKey = vpbxApiKey;
    }

    public VpbxCall getCall() {
        return call;
    }

    public String getVpbxApiKey() {
        return vpbxApiKey;
    }

    @Override
    public String toString() {
        return "CallEvent{" +
                "call=" + call +
                ", vpbxApiKey='" + vpbxApiKey + '\'' +
                '}';
    }
}
