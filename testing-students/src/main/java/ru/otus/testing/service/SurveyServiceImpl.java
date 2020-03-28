package ru.otus.testing.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.testing.config.SurveyProperties;
import ru.otus.testing.model.Question;
import ru.otus.testing.model.QuestionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final LocalizationService localizationService;

    private final SurveyProperties surveyProperties;

    private Map<Integer, String> fixAnswers;
    private String fio;

    public SurveyServiceImpl(QuestionService questionService, MessageSource messageSource, LocalizationService localizationService, SurveyProperties surveyProperties) {
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.localizationService = localizationService;
        this.surveyProperties = surveyProperties;

        this.fixAnswers = new HashMap<>();
    }

    @Override
    public void runTest() {
        Scanner scanner = new Scanner(System.in);
        this.initFio(scanner);
        this.runSurvey(scanner);
        this.finishTest();
    }

    private void initFio(Scanner scanner) {
        System.out.println(messageSource.getMessage("enter.name", null, this.localizationService.getCurrentLocale()));
        String name = scanner.nextLine();
        System.out.println(messageSource.getMessage("enter.surname", null, this.localizationService.getCurrentLocale()));
        String lastName = scanner.nextLine();
        this.fio = name + " " + lastName;
    }

    private void runSurvey(Scanner scanner) {
        List<Question> questions = this.questionService.getQuestions();
        for (Question question : questions) {
            this.printQuestion(question);
            String answer = scanner.nextLine();
            this.fixAnswer(question.getId(), answer);
        }
    }

    private void fixAnswer(Integer questionId, String answer) {
        this.fixAnswers.put(questionId, answer);
    }

    private void finishTest() {
        List<Question> questions = this.questionService.getQuestions();
        int countRightAnswer = evaluateCountRightAnswer(questions);

        this.printResultTest(questions.size(), countRightAnswer, countRightAnswer > surveyProperties.getMinCountRightAnswer());
    }

    private int evaluateCountRightAnswer(List<Question> questions) {
        int countRightAnswer = 0;
        for (Question question : questions) {
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
            System.out.println(messageSource.getMessage("answer.choice", null, this.localizationService.getCurrentLocale()));
            for (int i = 0; i < question.getQuantityChoiceAnswer(); i++) {
                System.out.println(i + ")" + question.getChoiceAnswer().get(i));
            }
        }
    }

    private void printResultTest(int countQuestion, int rightAnswer, boolean completeSuccess) {
        System.out.println(messageSource.getMessage("test.finish", null, this.localizationService.getCurrentLocale()) + ". " +
                messageSource.getMessage("entrant", null, this.localizationService.getCurrentLocale()) + ": " + this.fio);
        System.out.println(messageSource.getMessage("quantity.question", null, this.localizationService.getCurrentLocale()) + ": " + countQuestion);
        System.out.println(messageSource.getMessage("right.answer", null, this.localizationService.getCurrentLocale()) + ": " + rightAnswer);
        if (completeSuccess) {
            System.out.println(messageSource.getMessage("test.complete.success", null, this.localizationService.getCurrentLocale()));
        } else {
            System.out.println(messageSource.getMessage("test.complete.fail", null, this.localizationService.getCurrentLocale()));
        }
    }

}
