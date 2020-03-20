package ru.otus.testing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("localization-settings")
public class LocalizationProperties {

    private String enQuestionPath;
    private String ruQuestionPath;

    public String getEnQuestionPath() {
        return enQuestionPath;
    }

    public void setEnQuestionPath(String enQuestionPath) {
        this.enQuestionPath = enQuestionPath;
    }

    public String getRuQuestionPath() {
        return ruQuestionPath;
    }

    public void setRuQuestionPath(String ruQuestionPath) {
        this.ruQuestionPath = ruQuestionPath;
    }
}
