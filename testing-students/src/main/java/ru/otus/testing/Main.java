package ru.otus.testing;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.testing.service.SurveyService;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        SurveyService surveyService = context.getBean(SurveyService.class);
        surveyService.runTest();
        context.close();
    }
}