package ru.otus.testing.service;

import org.springframework.stereotype.Service;
import ru.otus.testing.config.LocalizationProperties;
import ru.otus.testing.exception.UnsupportedLocalException;

import java.util.Locale;

@Service
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
    public void setCurrentLocale(String localeName) throws UnsupportedLocalException {
        if (localizationProperties.getLocal().containsKey(localeName.toLowerCase())) {
            this.currentLocale = Locale.forLanguageTag(localizationProperties.getLocal().get(localeName.toLowerCase()));
        } else {
            throw new UnsupportedLocalException(localeName);
        }
    }

    @Override
    public String getResourceNameForCurrentLocale() throws UnsupportedLocalException {
        String resourceName = localizationProperties.getLocalPath().get(currentLocale.toLanguageTag());
        if (resourceName != null) {
            return resourceName;
        }
        throw new UnsupportedLocalException(currentLocale.getLanguage());
    }
}
