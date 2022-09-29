package com.ossjk.qlh.exams.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.exams.entity.Exampaper;
import com.ossjk.qlh.exams.vo.ExampaperVo;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.mapper
 * @ClassName: ExampaperMapper
 * @Description: 试卷-Mapper
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
public interface ExampaperMapper extends BaseMapper<Exampaper> {

    @Select("select exampaper.*,user.name as crerName from exampaper " +
            "left join user on exampaper.crer = user.id " +
            "${ew.customSqlSegment}")
    Page<ExampaperVo> pageExampaperVo(Page page, QueryWrapper<ExampaperVo> ew);
}
