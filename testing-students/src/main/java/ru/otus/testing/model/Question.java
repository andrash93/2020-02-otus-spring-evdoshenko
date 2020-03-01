package ru.otus.testing.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private Integer id;
    private String questionText;
    private QuestionType questionType;
    private Integer quantityChoiceAnswer;
    private Integer quantityRightAnswer;
    private List<String> choiceAnswer;
    private List<String> rightAnswer;

    public Question() {
        this.choiceAnswer = new ArrayList<>();
        this.rightAnswer = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<String> getChoiceAnswer() {
        return choiceAnswer;
    }

    public void addChoiceAnswer(String choiceAnswer) {
        this.choiceAnswer.add(choiceAnswer);
    }

    public Integer getQuantityChoiceAnswer() {
        return quantityChoiceAnswer;
    }

    public void setQuantityChoiceAnswer(Integer quantityChoiceAnswer) {
        this.quantityChoiceAnswer = quantityChoiceAnswer;
    }

    public Integer getQuantityRightAnswer() {
        return quantityRightAnswer;
    }

    public void setQuantityRightAnswer(Integer quantityRightAnswer) {
        this.quantityRightAnswer = quantityRightAnswer;
    }

    public List<String> getRightAnswer() {
        return rightAnswer;
    }

    public void addRightAnswer(String rightAnswer) {
        this.rightAnswer.add(rightAnswer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", questionType=" + questionType +
                ", quantityChoiceAnswer=" + quantityChoiceAnswer +
                ", quantityRightAnswer=" + quantityRightAnswer +
                ", choiceAnswer=" + choiceAnswer +
                ", rightAnswer=" + rightAnswer +
                '}';
    }
}
