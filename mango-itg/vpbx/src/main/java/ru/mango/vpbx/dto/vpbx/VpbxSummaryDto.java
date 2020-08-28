package ru.mango.vpbx.dto.vpbx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VpbxSummaryDto extends VpbxCall implements Serializable {

    @JsonProperty("call_direction")
    private Integer callDirection;
    @JsonProperty("line_number")
    private String lineNumber;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("from")
    private VpbxCallPartyDto from;
    @JsonProperty("to")
    private VpbxCallPartyDto to;
    @JsonProperty("talk_time")
    private String talkTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("entry_result")
    private Integer entryResult;
    @JsonProperty("disconnect_reason")
    private String disconnectReason;

    public Integer getCallDirection() {
        return callDirection;
    }

    public void setCallDirection(Integer callDirection) {
        this.callDirection = callDirection;
    }

    public Integer getEntryResult() {
        return entryResult;
    }

    public void setEntryResult(Integer entryResult) {
        this.entryResult = entryResult;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    @Override
    public String toString() {
        return "SummaryDto{" +
                "entryId='" + entryId + '\'' +
                ", callDirection='" + callDirection + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", createTime='" + createTime + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", talkTime='" + talkTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", entryResult='" + entryResult + '\'' +
                ", disconnectReason='" + disconnectReason + '\'' +
                '}';
    }
}
