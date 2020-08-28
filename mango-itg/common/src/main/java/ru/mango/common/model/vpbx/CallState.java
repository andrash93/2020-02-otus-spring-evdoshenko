package ru.mango.common.model.vpbx;

public enum CallState {

    Appeared("Appeared"),
    Connected("Connected"),
    Disconnected("Disconnected"),
    ;

    private final String state;

    CallState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
