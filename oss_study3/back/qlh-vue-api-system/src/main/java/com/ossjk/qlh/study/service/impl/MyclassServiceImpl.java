package com.ossjk.qlh.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.entity.Myclass;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.mapper.FeedbackMapper;
import com.ossjk.qlh.study.mapper.MyclassMapper;
import com.ossjk.qlh.study.mapper.SelfevaluationMapper;
import com.ossjk.qlh.study.mapper.SummarizeMapper;
import com.ossjk.qlh.study.service.IMyclassService;
import com.ossjk.qlh.study.vo.MyclassVo;

import com.ossjk.qlh.study.vo.NewStatisticalChartVo;
import com.ossjk.qlh.study.vo.OneSqlStatisticalChartVo;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Copyright  2022-06-16 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.service.impl
 * @ClassName: MyclassServiceImpl
 * @Description: -服务实现类
 * @author: Rick.yang
 * @date:  2022-06-16 17:38:14 
 */
@Service
public class MyclassServiceImpl extends ServiceImpl<MyclassMapper, Myclass> implements IMyclassService {

   @Autowired
    private StudentMapper studentMapper;
   @Autowired
   private SummarizeMapper summarizeMapper;
   @Autowired
   private SelfevaluationMapper selfevaluationMapper;
   @Autowired
   private FeedbackMapper feedbackMapper;
    @Override
    public Page<MyclassVo> selectVo(Page page, QueryWrapper<Myclass> qw) {
        return this.baseMapper.selectVo(page,qw);
    }

    @Override
    public ResponseBean<NewStatisticalChartVo> getSummarizeAll2(String cid, int dateFlag) {

//
//
//        //查询所有人
//        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
//        studentQueryWrapper.eq("cid",cid);
//        List<Student> students = studentMapper.selectList(studentQueryWrapper);
//        int studentAll = students.size();
//
//        //生成日期数组
//        LocalDate[] dates=new LocalDate[7]; ////////////
//        LocalDate old = LocalDate.now();
//        LocalDate now = old.plusDays(dateFlag * 7);
//        for (int i = 0; i < 7; i++) {
//            LocalDate localDate = now.plusDays(i - 6);
//            dates[i] = localDate;
//        }
//
//        //查询所有已提每日反馈交学生（去重的）
//        int[] submitStudentArray = new int[7];
//        int flag=0;
//        Iterator<LocalDate> iterator = Arrays.stream(dates).iterator();
//        while (iterator.hasNext()){
//            List<Summarize> summarizes = summarizeMapper.selectSubmitStudent(iterator.next(), cid);
//            submitStudentArray[flag]=summarizes.size();
//            flag++;
//        }
//        //未提交学生
//        int[] notSubmitStudentArray = new int[7];
//        for (int i = 0; i <notSubmitStudentArray.length ; i++) {
//            notSubmitStudentArray[i]=studentAll-submitStudentArray[i];
//        }
//
//        //查询所有已提自我评价交学生（去重的）
//        int[] selfevaluationSubmitStudentArray = new int[7];
//        int flag1=0;
//        Iterator<LocalDate> iterator1 = Arrays.stream(dates).iterator();
//        while (iterator1.hasNext()){
//            List<Selfevaluation> selfevaluations = selfevaluationMapper.selectSubmitStudent(iterator1.next(),cid);
//            selfevaluationSubmitStudentArray[flag1]=selfevaluations.size();
//            flag1++;
//        }
//        //未提交学生
//        int[] selfevaluationNotSubmitStudentArray = new int[7];
//        for (int i = 0; i < selfevaluationNotSubmitStudentArray.length; i++) {
//            selfevaluationNotSubmitStudentArray[i]=studentAll-selfevaluationSubmitStudentArray[i];
//        }
//
//       //查询所有已提每日总结交学生（去重的）
//        int[] feedbackSubmitStudentArray = new int[7];
//        //每日总结及格学生初始化
//        int[] feedbackSubmitPassStudentArray = new int[7];
//        //每日总结不及格学生初始化
//        int[] feedbackSubmitNotPassStudentArray = new int[7];
//        int flag2=0;
//        Iterator<LocalDate> iterator2 = Arrays.stream(dates).iterator();
//        while (iterator2.hasNext()){
//            LocalDate next = iterator2.next();
//            List<Feedback> feedbacks = feedbackMapper.selectDateList(next, cid);
//            feedbackSubmitStudentArray[flag2]=feedbacks.size();
//            //及格学生数量载入
//            Iterator<Feedback> feedbacksIterator = feedbacks.iterator();
//            int passFlag=0;
//            while (feedbacksIterator.hasNext()){
//                if ( feedbacksIterator.next().getScore()>2){
//                    passFlag++;
//                }
//            }
//            feedbackSubmitPassStudentArray[flag2]=passFlag;
//            //不及格学生数量载入
//            feedbackSubmitNotPassStudentArray[flag2]=feedbacks.size()-passFlag;
//            flag2++;
//        }
//        //未提交学生
//        int[] feedbackNotSubmitStudentArray = new int[7];
//        for (int i = 0; i < feedbackNotSubmitStudentArray.length; i++) {
//            feedbackNotSubmitStudentArray[i]=studentAll-feedbackSubmitStudentArray[i];
//        }
//
//
//
//
//
//
//
//        NewStatisticalChartVo newStatisticalChartVo = new NewStatisticalChartVo();
//        newStatisticalChartVo.setDateArray(dates);
//        newStatisticalChartVo.setNotSubmitStudentArray(notSubmitStudentArray);
//        newStatisticalChartVo.setSubmitStudentArray(submitStudentArray);
//        newStatisticalChartVo.setSelfevaluationSubmitStudentArray(selfevaluationSubmitStudentArray);
//        newStatisticalChartVo.setSelfevaluationNotSubmitStudentArray(selfevaluationNotSubmitStudentArray);
//        newStatisticalChartVo.setFeedbackSubmitStudentArray(feedbackSubmitStudentArray);
//        newStatisticalChartVo.setFeedbackNotSubmitStudentArray(feedbackNotSubmitStudentArray);
//
//        newStatisticalChartVo.setFeedbackSubmitPassStudentArray(feedbackSubmitPassStudentArray);
//        newStatisticalChartVo.setFeedbackSubmitNotPassStudentArray(feedbackSubmitNotPassStudentArray);
//
//
//

        return ResponseBean.Success(/*newStatisticalChartVo*/);
    }

    @Override
    public ResponseBean<List<Student>> getStudentList(String cid) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("cid",cid);

        List<Student> students = studentMapper.selectList(studentQueryWrapper);
        return ResponseBean.Success(students);
    }



    public ResponseBean<NewStatisticalChartVo> getStatisticalChart(String cid, int dateFlag) {
        List<OneSqlStatisticalChartVo> oneSqlStatisticalChartVos = this.baseMapper.oneSqlStatisticalChartVo(cid,dateFlag);
        Iterator<OneSqlStatisticalChartVo> iterator = oneSqlStatisticalChartVos.iterator();
        LocalDate[] dataArray = new LocalDate[7];
        int[] notSubmitStudentArray = new int[7];
        int[] submitStudentArray = new int[7];
        int[] selfevaluationSubmitStudentArray = new int[7];
        int[] selfevaluationNotSubmitStudentArray = new int[7];
        int[] feedbackSubmitStudentArray = new int[7];
        int[] feedbackNotSubmitStudentArray = new int[7];
        int[] feedbackSubmitPassStudentArray = new int[7];
        int[] feedbackSubmitNotPassStudentArray = new int[7];
        int flag=0;
        while (iterator.hasNext()){
            OneSqlStatisticalChartVo next = iterator.next();
            dataArray[flag]= next.getDate();
            submitStudentArray[flag]=next.getSummarizeSubmitted();
            notSubmitStudentArray[flag]=next.getSummarizeNotSubmitted();
            selfevaluationSubmitStudentArray[flag]=next.getSelfevaluationSubmitted();
            selfevaluationNotSubmitStudentArray[flag]=next.getSelfevaluationNotSubmitted();
            feedbackSubmitStudentArray[flag]=next.getFeedbackSubmitted();
            feedbackNotSubmitStudentArray[flag]=next.getFeedbackNotSubmitted();
            feedbackSubmitPassStudentArray[flag]=next.getFeedbackPass();
            feedbackSubmitNotPassStudentArray[flag]=next.getFeedbackNotPass();
            flag++;
        }
        NewStatisticalChartVo newStatisticalChartVo = new NewStatisticalChartVo();
        newStatisticalChartVo.setDateArray(dataArray);
        newStatisticalChartVo.setNotSubmitStudentArray(notSubmitStudentArray);
        newStatisticalChartVo.setSubmitStudentArray(submitStudentArray);
        newStatisticalChartVo.setSelfevaluationSubmitStudentArray(selfevaluationSubmitStudentArray);
        newStatisticalChartVo.setSelfevaluationNotSubmitStudentArray(selfevaluationNotSubmitStudentArray);
        newStatisticalChartVo.setFeedbackSubmitStudentArray(feedbackSubmitStudentArray);
        newStatisticalChartVo.setFeedbackNotSubmitStudentArray(feedbackNotSubmitStudentArray);

        newStatisticalChartVo.setFeedbackSubmitPassStudentArray(feedbackSubmitPassStudentArray);
        newStatisticalChartVo.setFeedbackSubmitNotPassStudentArray(feedbackSubmitNotPassStudentArray);
        return ResponseBean.Success(newStatisticalChartVo);
    }


}
