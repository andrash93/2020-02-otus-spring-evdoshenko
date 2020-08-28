package ru.mango.common.model.vpbx;

public enum CallDirection {

    inbound("inbound"),
    outbound("outbound"),
    internal("internal"),
    ;

    private final String direction;

    CallDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public static CallDirection getCallDirection(int direction) {
        if (direction == 1) {
            return inbound;
        }
        if (direction == 2) {
            return outbound;
        }
        return internal;

    }
}
