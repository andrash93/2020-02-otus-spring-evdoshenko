package ru.otus.testing.service;

import ru.otus.testing.model.Question;
import ru.otus.testing.model.QuestionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SurveyServiceImpl implements SurveyService {

    private final QuestionService questionService;

    private Map<Integer, String> fixAnswers;
    private int currentQuestionIdx;
    private String fio;

    public SurveyServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
        this.fixAnswers = new HashMap<>();
        this.currentQuestionIdx = 0;
    }

    @Override
    public void runTest() {
        this.initFio();
        this.runSurvey();
        this.finishTest();
    }

    private void initFio() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию ");
        String lastName = scanner.nextLine();
        scanner.close();
        this.fio = name + " " + lastName;
    }

    private void runSurvey() {
        Scanner scanner = new Scanner(System.in);
        int quantityQuestion = this.quantityQuestions();
        for (int i = 0; i < quantityQuestion; i++) {
            Question question = this.questionService.getQuestion(this.currentQuestionIdx++);
            this.printQuestion(question);
            this.fixAnswer(question.getId(), scanner.nextLine());
        }
        scanner.close();
    }

    private void fixAnswer(Integer questionId, String answer) {
        this.fixAnswers.put(questionId, answer);
    }

    private int quantityQuestions() {
        return this.questionService.quantityQuestions();
    }

    private void finishTest() {
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
