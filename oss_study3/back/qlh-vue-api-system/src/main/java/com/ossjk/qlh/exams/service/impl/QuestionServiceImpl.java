package com.ossjk.qlh.exams.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ossjk.myUtil.ObjectUtil;
import com.ossjk.qlh.edu.service.ISubjectService;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.mapper.QuestionMapper;
import com.ossjk.qlh.exams.service.IQuestionService;
import com.ossjk.qlh.exams.vo.QuestionSubVo;
import com.ossjk.qlh.stus.entity.Student;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.service.impl
 * @ClassName: QuestionServiceImpl
 * @Description: 试题表-服务实现类
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Autowired
    ISubjectService iSubjectService;
    @Override
    public Page<QuestionSubVo> selectQuSubVo(Page page, QueryWrapper<Question> qw) {

        return this.baseMapper.selectQuSubVo(page,qw);
    }

    @Override
    public List<Question> parseQuestion(File fname) {
        //解析excel文件
        try {
            FileInputStream in = new FileInputStream(fname);
            List<Question> lqs =  ExcelImportUtil.importExcel(in, Question.class,new ImportParams());
            List<Question> qs=new ArrayList<>();

            for (Question q:lqs) {
//
                if (!ObjectUtil.objectCheckIsNull(q)) {
                    qs.add(q);
                }
            }

            in.close();
            fname.delete();
            return qs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }



}
