package ru.otus.testing.service;

import ru.otus.testing.config.LocalizationProperties;

import java.util.Locale;

public class LocalizationServiceImpl implements LocalizationService {

    private final LocalizationProperties localizationProperties;

    private Locale currentLocale;

    public LocalizationServiceImpl(LocalizationProperties localizationProperties) {
        this.localizationProperties = localizationProperties;
    }

    @Override
    public Locale getCurrentLocale() {
        return this.currentLocale;
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

    @Override
    public String getResourceNameForCurrentLocale() {
        if (currentLocale.equals(Locale.forLanguageTag("ru-RU"))) {
            return localizationProperties.getRuQuestionPath();
        }
        if (currentLocale.equals(Locale.forLanguageTag("en-US"))) {
            return localizationProperties.getEnQuestionPath();
        }
        return localizationProperties.getRuQuestionPath();
    }
}
