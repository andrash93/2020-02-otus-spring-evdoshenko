package ru.otus.testing.service;


import ru.otus.testing.model.Question;

public interface QuestionService {

    Question getQuestion(int idx);

    int quantityQuestions();

}
