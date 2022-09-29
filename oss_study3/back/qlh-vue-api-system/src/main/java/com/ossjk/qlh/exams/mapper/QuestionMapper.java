package com.ossjk.qlh.exams.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.exams.entity.Question;
import com.ossjk.qlh.exams.vo.QuestionSubVo;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.exams.mapper
 * @ClassName: QuestionMapper
 * @Description: 试题表-Mapper
 * @author:
 * @date: 2022-06-14 11:31:17
 */
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("SELECT question.*,`subject`.`name` as sbname FROM `question` LEFT JOIN `subject` on question.sbid=`subject`.id ${ew.customSqlSegment}")
    Page<QuestionSubVo> selectQuSubVo(Page page, QueryWrapper<Question> ew);

}
