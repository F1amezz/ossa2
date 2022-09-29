package com.ossjk.qlh.exams.controller;

import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.service.IExamsEchService;
import com.ossjk.qlh.exams.vo.ExamsEchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright  2022-08-09 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: ExamsController
 * @Description: examsEch-控制器
 * @author:
 * @date: 2022-08-09 11:31:17
 */
@Api(tags = "试卷详情分析图标Ech")
@RestController
@RequestMapping("/exams/examsEch")
public class ExamsEchController extends BaseController {

    @Autowired
    private IExamsEchService iExamsEchService;

    @GetMapping("/getExamsEch")
    public ResponseBean<ExamsEchVo> getExamsEch(@ApiParam(value = "id") @RequestParam(name = "id") String id){
        ExamsEchVo examsEchVo = iExamsEchService.selectExamsEch(id);
        System.out.println(examsEchVo);
        return ResponseBean.Success(examsEchVo);
    }

    @GetMapping("/getExamsEchOne")
    public ResponseBean<List<ExamsEchVo>> getExamsEchOne(@ApiParam(value = "id") @RequestParam(name = "id") String id){
        List<ExamsEchVo> examsEchVo = iExamsEchService.selectExamsEchOne(id);
        System.out.println(examsEchVo);
        return ResponseBean.Success(examsEchVo);
    }

}
