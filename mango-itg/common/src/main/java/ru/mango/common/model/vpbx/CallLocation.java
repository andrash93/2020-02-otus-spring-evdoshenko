package ru.mango.common.model.vpbx;

public enum CallLocation {

    ivr("ivr"),
    queue("queue"),
    abonent("abonent"),
    ;

    private final String location;

    CallLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
