package ru.otus.testing.service;


import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.model.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private List<Question> questions;


    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getQuestions() {
        if (CollectionUtils.isEmpty(this.questions)) {
            this.questions = this.questionDao.findAllQuestion();
        }
        return this.questionDao.findAllQuestion();
    }

}
