package com.ossjk.qlh.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.system.entity.Department;

/** 
 * Copyright  2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: DepartmentMapper
 * @Description: 部门表-Mapper
 * @author: chair
 * @date:  2020-12-14 22:06:30 
 */
public interface DepartmentMapper extends BaseMapper<Department> {

	List<Department> selectByUid(@Param("uid")String uid);


}
