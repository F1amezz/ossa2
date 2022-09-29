package com.ossjk.qlh.study.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OneSqlStatisticalChartVo {
    private int summarizeNotSubmitted  ;
    private int summarizeSubmitted;
    private LocalDate date;
    private int selfevaluationSubmitted  ;
    private int selfevaluationNotSubmitted;
    private int feedbackSubmitted;
    private int feedbackNotSubmitted  ;
    private int feedbackPass;
    private int feedbackNotPass  ;

}
