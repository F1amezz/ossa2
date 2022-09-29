package com.ossjk.qlh.study.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.study.entity.Summarize;
import com.ossjk.qlh.study.vo.SummarizeVo;

/**
 * Copyright 2022-06-13 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.mapper
 * @ClassName: SummarizeMapper
 * @Description: -Mapper
 * @author: Rick.yang
 * @date: 2022-06-13 18:03:21
 */
public interface SummarizeMapper extends BaseMapper<Summarize> {

    @Select("SELECT * FROM `v_summarize` ${ew.customSqlSegment}")
    Page<SummarizeVo> selectVo(Page page, QueryWrapper<SummarizeVo> ew);

    @Select("SELECT s.`name` sname,t.`name` tname,c.`name` cname,c2.`name` course,`summarize`.* FROM `summarize`,`course` c2,`user` t,`clazz` c,`student` s WHERE c.`cid`=c2.`id` AND c.`id`=s.`cid` AND c.`tid`=t.`id` AND `summarize`.`sid`=s.`id` ${ew.customSqlSegment}")
    List<SummarizeVo> selectVoList(QueryWrapper<SummarizeVo> ew);

    @Select("SELECT s.`name` sname,t.`name`tname,c.`name` cname,c2.`name` course,`summarize`.* FROM `summarize`,"
            + "`course` c2,`user` t,`clazz` c,`student` s WHERE c.`cid`=c2.`id` AND c.`id`=s.`cid` AND c.`tid`=t.`id`  AND `summarize`.`sid`=s.`id` AND summarize.id=#{id}")
    SummarizeVo selectVoById(@Param("id") String id);

}
