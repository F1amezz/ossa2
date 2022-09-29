package com.ossjk.qlh.exams.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.entity.Answers;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.service.IAnswersService;
import com.ossjk.qlh.exams.service.IExamsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 答卷（题）表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/answers")
public class AnswersController {
    @Autowired
    private IExamsService iExamsService;

    @Autowired
    private IAnswersService iAnswersService;




    @ApiOperation(value = "初始化答案表。")
//	@RequiresPermissions("")
    @GetMapping("/saveAnswers")
    public ResponseBean saveAnswers(@ApiParam(value = "考试id") @RequestParam(name = "id") String id,
                                    @ApiParam(value = "expid") @RequestParam(name = "expid") String expid,
                                    @ApiParam(value = "cid") @RequestParam(name = "cid") String cid,
                                    @ApiParam(value = "stuid") @RequestParam(name = "stuid") String stuid) {


        QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
        answersQueryWrapper.eq("kid", id).eq("expid",expid).eq("cid",cid).eq("sid",stuid);

        Answers answers = iAnswersService.getOne(answersQueryWrapper);
        if (answers==null){
            Answers saveAnswers = new Answers();
            saveAnswers.setKid(id);
            saveAnswers.setExpid(expid);
            saveAnswers.setCid(cid);
            saveAnswers.setSid(stuid);
            long l = System.currentTimeMillis();
            saveAnswers.setStartexam(l);
            boolean save = iAnswersService.save(saveAnswers);
            Exampaper exampaper = iExamsService.selectExampaperById(expid);
            Integer duration = exampaper.getDuration();
            long timeDown=duration*60*1000;
            return ResponseBean.Success("开始考试",timeDown);
        }else {
            Exampaper exampaper = iExamsService.selectExampaperById(expid);
            Integer duration = exampaper.getDuration();
            long timeDown=duration*60*1000-(System.currentTimeMillis()-answers.getStartexam());
            return ResponseBean.Success("已经交卷，不可重复考试",timeDown);

        }




    }

    @ApiOperation(value = "初始化答案表。")
//	@RequiresPermissions("")
    @GetMapping("/updateAnswers")
    public ResponseBean updateAnswers(@ApiParam(value = "考试id") @RequestParam(name = "kid") String kid,
                                    @ApiParam(value = "expid") @RequestParam(name = "expid") String expid,
                                    @ApiParam(value = "cid") @RequestParam(name = "cid") String cid,
                                     @ApiParam(value = "sid") @RequestParam(name = "sid") String sid,
                                     @ApiParam(value = "answer") @RequestParam(name = "answer") String answer) {


        Answers answers = new Answers();
        answers.setAnswer(answer);
        answers.setStartexam(0l);

        QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
        answersQueryWrapper.eq("kid",kid).eq("expid",expid).eq("cid",cid).eq("sid",sid);

        iAnswersService.update(answers, answersQueryWrapper);




        return null;
    }


    @ApiOperation(value = "初始化答案表。")
//	@RequiresPermissions("")
    @GetMapping("/isCanReadFdbck")
    public ResponseBean isCanReadFeedback(@ApiParam(value = "考生id") @RequestParam(name = "sid") String sid ) {
              return ResponseBean.Success(this.iAnswersService.selectExamInTime(sid,new Date()));
    }

    @ApiOperation(value = "查看答案。")
//	@RequiresPermissions("")
    @GetMapping("/getOneAnswers")
    public ResponseBean getOneAnswers(@ApiParam(value = "考试id") @RequestParam(name = "id") String id,
                                    @ApiParam(value = "expid") @RequestParam(name = "expid") String expid,
                                    @ApiParam(value = "cid") @RequestParam(name = "cid") String cid,
                                    @ApiParam(value = "stuid") @RequestParam(name = "stuid") String stuid) {

        QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
        answersQueryWrapper.eq("sid",stuid).eq("expid",expid).eq("kid",id).eq("cid",cid);
        Answers answers = iAnswersService.getOne(answersQueryWrapper);

        return ResponseBean.Success(answers);
    }

}

