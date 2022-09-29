package com.ossjk.qlh.system.mapper;

import com.ossjk.qlh.system.vo.StudentVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.system.entity.Student;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: UserMapper
 * @Description: 用户表-Mapper
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
public interface StudentMapper extends BaseMapper<Student> {


	@Select("select  r.id rid ,r.name sname ,d.id did ,d.name dname, u.* from student u " +
			" LEFT JOIN  student_role  ur  on u.id =ur.uid LEFT JOIN  role  r  on ur.rid =r.id " +
			" LEFT JOIN  role_department  rd  on r.id =rd.rid LEFT JOIN  department  d  on d.id =rd.did ${ew.customSqlSegment}")

	Page selectPageVo(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);

	@Select("select  r.id rid ,r.name sname ,d.id did ,d.name dname, u.* from student u " +
            "LEFT JOIN  student_role  ur  on u.id =ur.uid  LEFT JOIN  role  r  on ur.rid =r.id " +
            "LEFT JOIN  role_department  rd  on r.id =rd.rid LEFT JOIN  department  d  on d.id =rd.did where u.id = #{id}")

    StudentVo selectVoById( @Param("id")Serializable id);

}
