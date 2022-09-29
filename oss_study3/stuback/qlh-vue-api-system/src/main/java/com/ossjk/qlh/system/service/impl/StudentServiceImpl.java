package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossjk.qlh.system.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.system.dto.StudentDto;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.entity.StudentRole;
import com.ossjk.qlh.system.mapper.StudentMapper;
import com.ossjk.qlh.system.service.IStudentRoleService;
import com.ossjk.qlh.system.service.IStudentService;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: UserServiceImpl
 * @Description: 用户表-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

	@Autowired
	private IStudentRoleService iUserRoleService;



	@Override
	public boolean saveDto(StudentDto dto) {
		boolean result = save(dto);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uid", dto.getId());
		iUserRoleService.remove(queryWrapper);
		StudentRole entity = new StudentRole();
		entity.setUid(dto.getId());
		entity.setRid(dto.getRoleId());
		iUserRoleService.save(entity);
		return result;
	}

	@Override
	public boolean updateDtoById(StudentDto dto) {
		boolean result = updateById(dto);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uid", dto.getId());
		iUserRoleService.remove(queryWrapper);
		StudentRole entity = new StudentRole();
		entity.setUid(dto.getId());
		entity.setRid(dto.getRoleId());
		iUserRoleService.save(entity);
		return result;
	}

	@Override
	public Page pageVo(Page page, Wrapper wrapper) {
		return this.baseMapper.selectPageVo(page, wrapper);
	}

	@Override
	public StudentVo getVoById(Serializable id) {
		return this.baseMapper.selectVoById(id);
	}




}
