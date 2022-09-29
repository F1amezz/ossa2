package com.ossjk.qlh.study.mapper;

import java.time.LocalDate;
import java.util.List;

import com.ossjk.qlh.study.entity.Summarize;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.study.entity.Selfevaluation;
import com.ossjk.qlh.study.vo.SelfevaluationVo;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xuexi.mapper
 * @ClassName: SelfevaluationMapper
 * @Description: 自我评价表-Mapper
 * @author: Rick.yang
 * @date: 2022-06-14 17:12:17
 */
public interface SelfevaluationMapper extends BaseMapper<Selfevaluation> {

    @Select("SELECT * from v_selfevaluation ${ew.customSqlSegment}")
    Page<SelfevaluationVo> selectVo(Page page, QueryWrapper<SelfevaluationVo> ew);

    @Select("SELECT * from v_selfevaluation ${ew.customSqlSegment}")
    List<SelfevaluationVo> selectVo(QueryWrapper<SelfevaluationVo> ew);



    @Select("select distinct sid from selfevaluation where cid=#{cid} and date(subtime)=#{date};")
    List<Selfevaluation> selectSubmitStudent(LocalDate date, String cid);

}
