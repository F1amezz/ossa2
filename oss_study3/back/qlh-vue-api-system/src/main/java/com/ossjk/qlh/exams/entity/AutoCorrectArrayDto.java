package com.ossjk.qlh.exams.entity;

import lombok.Data;

@Data
public class AutoCorrectArrayDto {
    private String[] choiceQuestionAnswer;
    private String[] choiceQuestionId;
    private int[] choiceQuestionScore;
    private String[] multipleChoiceQuestionsAnswer;
    private String[] multipleChoiceQuestionsId;
    private int[] multipleChoiceQuestionsScore;
    private String[] TorFQuestionsAnswer;
    private String[] TorFQuestionsId;
    private int[] TorFQuestionsScore;
    private String[] completion;
    private String[] majorTopic1;
    private String[] majorTopic2;
    private String[] majorTopic3;
    private int[] completionScore;
    private int[] majorTopic1Score;
    private int[] majorTopic2Score;
    private int[] majorTopic3Score;


}
