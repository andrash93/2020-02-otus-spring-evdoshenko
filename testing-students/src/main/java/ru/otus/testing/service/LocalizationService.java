package ru.otus.testing.service;

import ru.otus.testing.exception.UnsupportedLocalException;

import java.util.Locale;

public interface LocalizationService {

    Locale getCurrentLocale();

    void setCurrentLocale(String localeName) throws UnsupportedLocalException;

    String getResourceNameForCurrentLocale() throws UnsupportedLocalException;

}
