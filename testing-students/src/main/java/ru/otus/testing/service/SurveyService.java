package ru.otus.testing.service;

public interface SurveyService {

    void startTest(String name, String lastName);

    void nextQuestion();

    void fixAnswer(String answer);

    int quantityQuestions();

    void finishTest();
}
