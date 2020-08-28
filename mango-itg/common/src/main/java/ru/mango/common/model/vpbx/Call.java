package ru.mango.common.model.vpbx;

public class Call {

    private String vpbxApiKey;
    private Long startTime;
    private Long connectedTime;
    private Long talkTime;
    private Long endTime;
    private CallLocation location;
    private CallState callState;
    private CallDirection callDirection;
    private CallEntryResult entryResult;
    private String phoneFrom;
    private String phoneTo;
    private String ext;

    public String getVpbxApiKey() {
        return vpbxApiKey;
    }

    public void setVpbxApiKey(String vpbxApiKey) {
        this.vpbxApiKey = vpbxApiKey;
    }

    public CallLocation getLocation() {
        return location;
    }

    public void setLocation(CallLocation location) {
        this.location = location;
    }

    public CallState getCallState() {
        return callState;
    }

    public void setCallState(CallState callState) {
        this.callState = callState;
    }

    public CallDirection getCallDirection() {
        return callDirection;
    }

    public void setCallDirection(CallDirection callDirection) {
        this.callDirection = callDirection;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getConnectedTime() {
        return connectedTime;
    }

    public void setConnectedTime(Long connectedTime) {
        this.connectedTime = connectedTime;
    }

    public String getPhoneFrom() {
        return phoneFrom;
    }

    public void setPhoneFrom(String phoneFrom) {
        this.phoneFrom = phoneFrom;
    }

    public String getPhoneTo() {
        return phoneTo;
    }

    public void setPhoneTo(String phoneTo) {
        this.phoneTo = phoneTo;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public CallEntryResult getEntryResult() {
        return entryResult;
    }

    public void setEntryResult(CallEntryResult entryResult) {
        this.entryResult = entryResult;
    }

    public Long getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Long talkTime) {
        this.talkTime = talkTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Call{" +
                "startTime=" + startTime +
                ", connectedTime=" + connectedTime +
                ", talkTime=" + talkTime +
                ", endTime=" + endTime +
                ", location=" + location +
                ", callState=" + callState +
                ", callDirection=" + callDirection +
                ", entryResult=" + entryResult +
                ", phoneFrom='" + phoneFrom + '\'' +
                ", phoneTo='" + phoneTo + '\'' +
                ", ext='" + ext + '\'' +
                '}';
    }
}
