package com.ossjk.qlh.study.mapper;

import java.time.LocalDate;
import java.util.List;

import com.ossjk.qlh.study.entity.Summarize;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.study.entity.Feedback;
import com.ossjk.qlh.study.vo.FeedbackVo;

/**
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.mapper
 * @ClassName: FeedbackMapper
 * @Description: 总结反馈表-Mapper
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {
    @Select("SELECT  * from v_feedback ${ew.customSqlSegment}")
    Page<FeedbackVo> selectVo(Page page, QueryWrapper<FeedbackVo> ew);

    @Select("SELECT  * from v_feedback ${ew.customSqlSegment}")
    List<FeedbackVo> selectVoList(QueryWrapper<FeedbackVo> ew);

    @Select("SELECT  * from v_feedback where id=#{id}")
    FeedbackVo selectVoById(@Param("id") String id);


    @Select("select sid,max(score) as score  from feedback where date(subtime)=#{date} and cid =#{cid} group by sid;")
    List<Feedback> selectDateList(LocalDate date, String cid);




}
