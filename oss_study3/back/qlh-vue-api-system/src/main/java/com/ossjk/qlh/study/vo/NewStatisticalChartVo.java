package com.ossjk.qlh.study.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewStatisticalChartVo {


    LocalDate[] DateArray;
    int[] submitStudentArray;
    int[] notSubmitStudentArray;
    int[] selfevaluationSubmitStudentArray;
    int[] selfevaluationNotSubmitStudentArray;

    int[] feedbackSubmitStudentArray;
    int[] feedbackNotSubmitStudentArray;

    int[] feedbackSubmitPassStudentArray;
    int[] feedbackSubmitNotPassStudentArray;
}
