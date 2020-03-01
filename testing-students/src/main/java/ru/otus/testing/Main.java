package ru.otus.testing;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.testing.service.SurveyService;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        SurveyService surveyService = context.getBean(SurveyService.class);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя ");
        String name = scanner.nextLine();
        System.out.print("Введите фамилию ");
        String lastName = scanner.nextLine();
        surveyService.startTest(name, lastName);
        for (int i = 0; i < surveyService.quantityQuestions(); i++) {
            surveyService.nextQuestion();
            surveyService.fixAnswer(scanner.nextLine());
        }
        surveyService.finishTest();
        scanner.close();
        context.close();
    }
}