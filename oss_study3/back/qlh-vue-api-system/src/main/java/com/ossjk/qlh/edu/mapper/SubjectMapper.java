package com.ossjk.qlh.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.edu.entity.Subject;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright 2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.edu.mapper
 * @ClassName: SubjectMapper
 * @Description: 科目表-Mapper
 * @author: lin
 * @date: 2022-06-14 19:24:30
 */
public interface SubjectMapper extends BaseMapper<Subject> {
    @Select("select s.id from subject s where s.name like concat('%',#{sname},'%')")
    String parseSidBySname(String sname);
}
