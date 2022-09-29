package com.ossjk.qlh.study.vo;

import lombok.Data;


import java.time.LocalDate;
import java.util.List;
@Data
public class StatisticalChartVo {


    LocalDate[] DateArray;
    int[] statisticalArray;
    int[] statisticalReadedArray;


}
