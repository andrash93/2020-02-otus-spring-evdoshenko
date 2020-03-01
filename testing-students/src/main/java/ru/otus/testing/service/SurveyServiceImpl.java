package ru.otus.testing.service;

import org.springframework.util.StringUtils;
import ru.otus.testing.model.Question;
import ru.otus.testing.model.QuestionType;

import java.util.HashMap;
import java.util.Map;


public class SurveyServiceImpl implements SurveyService {

    private final QuestionService questionService;

    private Question currentQuestion;
    private Map<Integer, String> fixAnswers;
    private int currentQuestionIdx;
    private String fio;

    public SurveyServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
        this.fixAnswers = new HashMap<>();
        this.currentQuestionIdx = 0;
    }

    @Override
    public void startTest(String name, String lastName) {
        this.fio = name + " " + lastName;
    }

    @Override
    public void nextQuestion() {
        if (StringUtils.isEmpty(this.fio)) {
            throw new RuntimeException("Test is not start");
        }
        this.currentQuestion = this.questionService.getQuestion(this.currentQuestionIdx++);
        printQuestion(this.currentQuestion);
    }

    @Override
    public void fixAnswer(String answer) {
        this.fixAnswers.put(this.currentQuestion.getId(), answer);
    }

    @Override
    public int quantityQuestions() {
        return this.questionService.quantityQuestions();
    }

    @Override
    public void finishTest() {
        printResultTest(this.questionService.quantityQuestions(), evaluateCountRightAnswer());
    }

    private int evaluateCountRightAnswer() {
        int countRightAnswer = 0;
        int currentQuestionIdx = 0;

        while (currentQuestionIdx < this.questionService.quantityQuestions()) {
            Question question = this.questionService.getQuestion(currentQuestionIdx++);
            String rightAnswer = String.join(",", question.getRightAnswer());
            String fixAnswer = this.fixAnswers.getOrDefault(question.getId(), "");
            if (fixAnswer.equals(rightAnswer)) {
                countRightAnswer++;
            }
        }

        return countRightAnswer;
    }

    private void printQuestion(Question question) {
        System.out.println(question.getQuestionText());
        if (question.getQuestionType() != QuestionType.FREE) {
            System.out.println("Варианты ответов: ");
            for (int i = 0; i < question.getQuantityChoiceAnswer(); i++) {
                System.out.println(i + ")" + question.getChoiceAnswer().get(i));
            }
        }
    }

    private void printResultTest(int countQuestion, int rightAnswer) {

        System.out.println("Тест завершен. Участник: " + this.fio);
        System.out.println("Всего вопросов: " + countQuestion);
        System.out.println("Правильных ответов : " + rightAnswer);
    }

}
