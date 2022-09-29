package com.ossjk.qlh.exams.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ossjk.qlh.exams.entity.Answers;
import com.ossjk.qlh.exams.mapper.AnswersMapper;
import com.ossjk.qlh.exams.service.IAnswersService;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.service.IFeedbackService;
import com.ossjk.qlh.study.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;


/**
 * <p>
 * 答卷（题）表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-08-01
 */
@Service
public class AnswersServiceImpl extends ServiceImpl<AnswersMapper, Answers> implements IAnswersService {



    public  Boolean selectExamInTime(String sid, Date nowTm){

        String paperId =  super.baseMapper.selectMaxNullExpID(sid);
        if(paperId == null){
            return true;
        }
        System.out.println("paperId= "+paperId);

        return  super.baseMapper.selectExamInTime(sid,  nowTm,  paperId)!=null;
    }

}
