package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.mapper.DepartmentMapper;
import com.ossjk.qlh.system.service.IDepartmentService;

import cn.hutool.core.util.ObjectUtil;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: DepartmentServiceImpl
 * @Description: 部门表-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

	@Override
	public boolean removeChildrenByIds(Collection<? extends Serializable> idList) {
		if (ObjectUtil.isNotEmpty(idList)) {
			for (Serializable id : idList) {
				if (!this.removeById(id)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeChildrenById(Serializable id) {
		List<Department> departments = listChildrenById(id);
		List<String> ids = departments.stream().map((t) -> {
			return t.getId();
		}).collect(Collectors.toList());
		return SqlHelper.retBool(baseMapper.deleteBatchIds(ids));
	}

	@Override
	public List<Department> listChildrenById(Serializable id) {
		List<Department> department = null;
		Department pdepartment = baseMapper.selectById(id);
		if (ObjectUtil.isNotEmpty(pdepartment)) {
			department = new ArrayList();
			department.add(pdepartment);
			listChildrenByPid(pdepartment.getId(), department);

		}
		return department;
	}

	@Override
	public List<Department> listChildrenByPid(Serializable pid, List<Department> parent) {
		if (ObjectUtil.isEmpty(parent)) {
			parent = new ArrayList<Department>();
		}
		QueryWrapper<Department> queryWrapper = new QueryWrapper();
		List<Department> children = baseMapper.selectList(queryWrapper.eq("pid", pid));
		if (ObjectUtil.isNotEmpty(children)) {
			parent.addAll(children);
			for (Department pdepartment : children) {
				listChildrenByPid(pdepartment.getId(), parent);
			}
		}

		return parent;
	}

	@Override
	public List<Department> listByUid(String uid) {
		return this.baseMapper.selectByUid(uid);
	}

}
