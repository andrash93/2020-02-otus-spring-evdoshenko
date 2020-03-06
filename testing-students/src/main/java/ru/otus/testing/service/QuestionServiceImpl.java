package ru.otus.testing.service;


import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private List<Question> questions;


    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void init() {
        this.questions = this.questionDao.findAllQuestion();
    }

    @Override
    public Question getQuestion(int idx) {
        if (idx < this.questions.size()) {
            return this.questions.get(idx);
        }
        throw new RuntimeException("idx > list size");
    }

    @Override
    public int quantityQuestions() {
        return this.questions.size();
    }
}
