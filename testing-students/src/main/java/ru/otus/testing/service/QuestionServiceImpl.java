package ru.otus.testing.service;


import org.springframework.util.CollectionUtils;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private List<Question> questions;


    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question getQuestion(int idx) {
        if(CollectionUtils.isEmpty(this.questions)){
            this.questions = this.questionDao.findAllQuestion();
        }

        if (idx < this.questions.size()) {
            return this.questions.get(idx);
        }

        throw new RuntimeException("idx > list size");
    }

    @Override
    public int quantityQuestions() {
        if(CollectionUtils.isEmpty(this.questions)){
            this.questions = this.questionDao.findAllQuestion();
        }
        return this.questions.size();
    }
}
