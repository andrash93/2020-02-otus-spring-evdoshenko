package ru.otus.testing.service;

import java.util.Locale;

public interface LocalizationService {

    Locale getCurrentLocale();

    void setCurrentLocale(Locale locale);

    String getResourceNameForCurrentLocale();

}
