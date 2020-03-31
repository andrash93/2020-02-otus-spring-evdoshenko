package ru.otus.testing.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("survey-settings")
public class SurveyProperties {

    private Integer minCountRightAnswer;

    public Integer getMinCountRightAnswer() {
        return minCountRightAnswer;
    }

    public void setMinCountRightAnswer(Integer minCountRightAnswer) {
        this.minCountRightAnswer = minCountRightAnswer;
    }
}
