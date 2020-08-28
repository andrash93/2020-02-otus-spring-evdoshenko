package ru.mango.vpbx.dto.vpbx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;
import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.common.model.vpbx.CallLocation;
import ru.mango.common.model.vpbx.CallState;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VpbxCallDto extends VpbxCall implements Serializable {

    @JsonProperty("call_id")
    private String callId;
    @JsonProperty("command_id")
    private String commandId;
    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("call_state")
    private CallState callState;
    @JsonProperty("location")
    private CallLocation location;
    @JsonProperty("from")
    private VpbxCallPartyDto from;
    @JsonProperty("to")
    private VpbxCallPartyDto to;
    @JsonProperty("disconnect_reason")
    private String disconnectReason;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public CallState getCallState() {
        return callState;
    }

    public void setCallState(CallState callState) {
        this.callState = callState;
    }

    public CallLocation getLocation() {
        return location;
    }

    public void setLocation(CallLocation location) {
        this.location = location;
    }

    public VpbxCallPartyDto getFrom() {
        return from;
    }

    public void setFrom(VpbxCallPartyDto from) {
        this.from = from;
    }

    public VpbxCallPartyDto getTo() {
        return to;
    }

    public void setTo(VpbxCallPartyDto to) {
        this.to = to;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public CallDirection getCallDirection() {
        if (this.getLocation() == CallLocation.ivr) {
            return CallDirection.inbound;
        } else if (!StringUtils.isEmpty(this.getFrom().getExtension()) && StringUtils.isEmpty(this.getTo().getExtension())) {
            return CallDirection.outbound;
        } else if (StringUtils.isEmpty(this.getFrom().getExtension()) && !StringUtils.isEmpty(this.getTo().getExtension())) {
            return CallDirection.inbound;
        } else {
            return CallDirection.internal;
        }
    }

    @Override
    public String toString() {
        return "CallDto{" +
                "entryId='" + entryId + '\'' +
                ", callId='" + callId + '\'' +
                ", commandId='" + commandId + '\'' +
                ", timestamp=" + timestamp +
                ", callState='" + callState + '\'' +
                ", location='" + location + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", disconnectReason='" + disconnectReason + '\'' +
                '}';
    }
}
