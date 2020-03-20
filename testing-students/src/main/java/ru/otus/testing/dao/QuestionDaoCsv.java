package ru.otus.testing.dao;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.testing.model.Question;
import ru.otus.testing.model.QuestionType;
import ru.otus.testing.service.LocalizationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao {

    private final ResourceLoader resourceLoader;
    private final LocalizationService localizationService;

    public QuestionDaoCsv(ResourceLoader resourceLoader, LocalizationService localizationService) {
        this.resourceLoader = resourceLoader;
        this.localizationService = localizationService;
    }

    @Override
    public List<Question> findAllQuestion() {

        Resource resource = this.resourceLoader.getResource(localizationService.getResourceNameForCurrentLocale());
        List<Question> questions = new ArrayList<>();

        try {
            String line = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                questions.add(parseQuestion(line));
            }
            bufferedReader.close();
            return questions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Question parseQuestion(String questionString) {
        String[] questionFields = questionString.split(",");
        Question question = new Question();

        int idx = 0;
        question.setId(Integer.valueOf(questionFields[idx++]));
        question.setQuestionType(QuestionType.valueOf(questionFields[idx++]));
        question.setQuestionText(questionFields[idx++]);

        int quantityChoiceAnswer = Integer.parseInt(questionFields[idx++]);
        question.setQuantityChoiceAnswer(quantityChoiceAnswer);
        for (int i = 0; i < quantityChoiceAnswer; i++) {
            question.addChoiceAnswer(questionFields[idx++]);
        }

        int quantityRightAnswer = Integer.parseInt(questionFields[idx++]);
        question.setQuantityRightAnswer(quantityRightAnswer);
        for (int i = 0; i < quantityRightAnswer; i++) {
            question.addRightAnswer(questionFields[idx++]);
        }

        return question;
    }

}
