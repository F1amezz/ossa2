package com.ossjk.qlh.exams.entity;

import lombok.Data;

import java.util.List;

@Data
public class QuestionsArray {
    List<Question> questions;
    String answersid;
}
