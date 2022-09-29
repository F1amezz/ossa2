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
import com.ossjk.qlh.system.entity.Permission;
import com.ossjk.qlh.system.mapper.PermissionMapper;
import com.ossjk.qlh.system.service.IPermissionService;

import cn.hutool.core.util.ObjectUtil;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: PermissionServiceImpl
 * @Description: 权限表-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

	@Override
	public boolean removeChildrenById(Serializable id) {
		List<Permission> permissions = listChildrenById(id);
		List<String> ids = permissions.stream().map((t) -> {
			return t.getId();
		}).collect(Collectors.toList());
		return SqlHelper.retBool(baseMapper.deleteBatchIds(ids));
	}

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
	public List<Permission> listChildrenById(Serializable id) {
		List<Permission> permissions = null;
		Permission permission = baseMapper.selectById(id);
		if (ObjectUtil.isNotEmpty(permission)) {
			permissions = new ArrayList();
			permissions.add(permission);
			listChildrenByPid(permission.getId(), permissions);
		}
		return permissions;
	}

	@Override
	public List<Permission> listChildrenByPid(Serializable pid, List<Permission> parent) {
		if (ObjectUtil.isEmpty(parent)) {
			parent = new ArrayList<Permission>();
		}
		QueryWrapper<Permission> queryWrapper = new QueryWrapper();
		List<Permission> children = baseMapper.selectList(queryWrapper.eq("pid", pid));
		if (ObjectUtil.isNotEmpty(children)) {
			parent.addAll(children);
			for (Permission permission : children) {
				listChildrenByPid(permission.getId(), parent);
			}
		}

		return parent;
	}

	@Override
	public List<Permission> listByUid(String uid) {
		return this.baseMapper.selectByUid(uid);
	}

}
