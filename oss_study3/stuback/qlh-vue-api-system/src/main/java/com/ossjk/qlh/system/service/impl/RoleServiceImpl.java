package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ossjk.qlh.system.dto.RoleDto;
import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.entity.Role;
import com.ossjk.qlh.system.entity.RoleDepartment;
import com.ossjk.qlh.system.entity.RolePermission;
import com.ossjk.qlh.system.mapper.RoleMapper;
import com.ossjk.qlh.system.service.IRoleDepartmentService;
import com.ossjk.qlh.system.service.IRolePermissionService;
import com.ossjk.qlh.system.service.IRoleService;

import cn.hutool.core.util.ObjectUtil;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service.impl
 * @ClassName: RoleServiceImpl
 * @Description: 角色表-服务实现类
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Autowired
	private IRolePermissionService iRolePermissionService;
	@Autowired
	private IRoleDepartmentService iRoleDepartmentService;

	@Override
	public boolean saveDto(RoleDto entity) {
		save(entity);
		updateRoleDepartment(entity.getId(), entity.getDepartmentId());
		updateRolePermission(entity.getId(), entity.getPermissionIds());
		return true;
	}

	@Override
	public String selectJXFMByRname( String rcode){
		return this.baseMapper.selectJXFMByRname(rcode);
	}

	@Override
	public boolean updateDtoById(RoleDto entity) {
		updateById(entity);
		updateRoleDepartment(entity.getId(), entity.getDepartmentId());
		updateRolePermission(entity.getId(), entity.getPermissionIds());
		return true;
	}

	private void updateRoleDepartment(String rid, String departmentId) {
		// 先删除数据
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("rid", rid);
		iRoleDepartmentService.remove(queryWrapper);
		// 新增数据
		RoleDepartment roleDepartment = new RoleDepartment();
		roleDepartment.setDid(departmentId);
		roleDepartment.setRid(rid);
		iRoleDepartmentService.save(roleDepartment);
	}

	private void updateRolePermission(String rid, List<String> permissionIds) {
		// 先删除数据
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("rid", rid);
		iRolePermissionService.remove(queryWrapper);
		// 新增数据
		List<RolePermission> rolePermissions = new ArrayList<RolePermission>();
		for (String permissionId : permissionIds) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPid(permissionId);
			rolePermission.setRid(rid);
			rolePermissions.add(rolePermission);
		}
		iRolePermissionService.saveBatch(rolePermissions);
	}

	@Override
	public boolean removeChildrenById(Serializable id) {
		List<Role> roles = listChildrenById(id);
		List<String> ids = roles.stream().map((t) -> {
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
	public List<Role> listChildrenById(Serializable id) {
		List<Role> role = null;
		Role prole = baseMapper.selectById(id);
		if (ObjectUtil.isNotEmpty(prole)) {
			role = new ArrayList();
			role.add(prole);
			listChildrenByPid(prole.getId(), role);

		}
		return role;
	}

	@Override
	public List<Role> listChildrenByPid(Serializable pid, List<Role> parent) {
		if (ObjectUtil.isEmpty(parent)) {
			parent = new ArrayList<Role>();
		}
		QueryWrapper<Role> queryWrapper = new QueryWrapper();
		List<Role> children = baseMapper.selectList(queryWrapper.eq("pid", pid));
		if (ObjectUtil.isNotEmpty(children)) {
			parent.addAll(children);
			for (Role prole : children) {
				listChildrenByPid(prole.getId(), parent);
			}
		}

		return parent;
	}

	@Override
	public List<Role> listByUid(String uid) {
		return this.baseMapper.selectByUid(uid);
	}

}
