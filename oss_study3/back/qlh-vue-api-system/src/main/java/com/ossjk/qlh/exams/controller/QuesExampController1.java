package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.service.IExampaperService;
import com.ossjk.qlh.exams.service.IQuestionService;
import com.ossjk.qlh.exams.service.IQuestionService2;
import com.ossjk.qlh.exams.vo.QuestionExampVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/exams/quesExamp")
public class QuesExampController1 extends BaseController {

    @Autowired
    private IQuestionService2 iQuestionService2;

    @Autowired
    private IExampaperService iExampaperService;

    @GetMapping("/list")
    public ResponseBean<Page<QuestionExampVo>> list(Page page,
                                                  @ApiParam(value = "题目类型") @RequestParam(name = "typeId", required = false) String typeId,
                                                  @ApiParam(value = "科目Id") @RequestParam(name = "subIds", required = false) String[] subIds,
                                                  @ApiParam(value = "题目") @RequestParam(name = "name",required = false) String name,
                                                  @ApiParam(value = "试题来源") @RequestParam(name = "reference",required = false) String reference) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<Question>();
        if (StrUtil.isNotBlank(typeId)) {
            queryWrapper.eq("type", typeId);
        }
        if (subIds != null && subIds.length > 0) {
            queryWrapper.in("sbid", Arrays.asList(subIds));
        }
        if (StrUtil.isNotBlank(reference)) {
            queryWrapper.eq("reference", reference);
        }
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("question.name", name);
        }

        //根据创建时间降序排序
        queryWrapper.orderByDesc("mdtm");

        return ResponseBean.Success(iQuestionService2.selectQuSubVo(page, queryWrapper));
    }


    @GetMapping("/listQuestion")
    public ResponseBean<List<Question>> listQuestion(@ApiParam(value = "科目Id") @RequestParam(name = "quIds", required = false) String[] quIds) {

        ArrayList<Question> questionsList = new ArrayList<>();

        Iterator<String> iterator = Arrays.stream(quIds).iterator();
        while (iterator.hasNext()){
            Question question = iQuestionService2.getById(iterator.next());
            questionsList.add(question);
        }
        return ResponseBean.Success(questionsList);
    }




}
