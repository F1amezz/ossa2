package com.ossjk.qlh.exams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.exams.entity.Exams;
import com.ossjk.qlh.exams.vo.ExamsEchVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExamsEchMapper  extends BaseMapper<ExamsEchVo> {

    @Select("select " +
            "exams.sid, " +
            "count(answers.id) as couex, " +
            "max(answers.score) as maxscore,min(answers.score) as minscore," +
            "round(avg(answers.score),2) as avgscore  ," +
            "sum(case when answers.score >= exampaper.pass_score then 1 else 0 end ) as jige from answers " +
            "left join exampaper on answers.expid = exampaper.id " +
            "left join exams on answers.kid = exams.id " +
            "where answers.kid = #{id}")
    ExamsEchVo selectExamsEch(String id);

    @Select("select answers.score,student.name from answers " +
            "left join student on answers.sid = student.id " +
            "where answers.kid = #{id}")
    List<ExamsEchVo> selectExamsEchOne(String id);
}
