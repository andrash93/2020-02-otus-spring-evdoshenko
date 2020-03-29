package ru.otus.testing.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.testing.dao.QuestionDaoCsv;
import ru.otus.testing.model.Question;
import ru.otus.testing.model.QuestionType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionDaoCsv questionDaoCsv;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void parseSingleQuestionFromCsv() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(getSingleQuestion());

        when(questionDaoCsv.findAllQuestion()).thenReturn(questionList);

        List<Question> resultList = questionService.getQuestions();

        assertEquals(1, resultList.size());
        verify(questionDaoCsv, times(1)).findAllQuestion();

    }

    private Question getSingleQuestion() {
        Question question = new Question();
        question.setId(1);
        question.setQuestionType(QuestionType.SINGLE);
        question.setQuestionText("опрос номер (одиночный)");

        question.setQuantityChoiceAnswer(3);
        question.addChoiceAnswer("ответ1");
        question.addChoiceAnswer("ответ2");
        question.addChoiceAnswer("ответ3");

        question.setQuantityRightAnswer(1);
        question.addRightAnswer("ответ1");
        return question;
    }

}