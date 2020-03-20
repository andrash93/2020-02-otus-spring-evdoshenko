package ru.otus.testing.exception;

public class UnsupportedLocalException extends Exception {

    private final String unsupportedLocalName;

    public UnsupportedLocalException(String unsupportedLocalName) {
        this.unsupportedLocalName = unsupportedLocalName;
    }

    public String getUnsupportedLocalName() {
        return unsupportedLocalName;
    }
}
