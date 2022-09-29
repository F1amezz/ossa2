package com.ossjk.qlh.edu.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.edu.vo.ClazzVO;

/**
 * Copyright 2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.mapper
 * @ClassName: ClazzMapper
 * @Description: 班级表-Mapper
 * @author: Rick.yang
 * @date: 2022-06-08 19:34:03
 */
public interface ClazzMapper extends BaseMapper<Clazz> {

    @Select(" select * from v_clazz ${ew.customSqlSegment} ")
    Page<ClazzVO> pageVO(Page<ClazzVO> page, QueryWrapper<Clazz> ew);

}
