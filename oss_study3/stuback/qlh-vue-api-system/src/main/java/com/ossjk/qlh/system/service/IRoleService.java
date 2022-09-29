package com.ossjk.qlh.system.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.dto.RoleDto;
import com.ossjk.qlh.system.entity.Permission;
import com.ossjk.qlh.system.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: RoleService
 * @Description: 角色表-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 根据id删除子节点
	 * 
	 * @param id
	 * @return
	 */
	boolean removeChildrenById(Serializable id);


	String selectJXFMByRname( String rcode);

	/**
	 * 根据ids删除子节点（包含自身）
	 * 
	 * @param idList
	 * @return
	 */
	boolean removeChildrenByIds(Collection<? extends Serializable> idList);

	/**
	 * 查找所有子节点（包含自身）
	 * 
	 * @param id
	 * @return
	 */
	public List<Role> listChildrenById(Serializable id);

	/**
	 * 查找所有子节点（不包含自身）
	 * 
	 * @param pid
	 * @param parent
	 * @return
	 */
	public List<Role> listChildrenByPid(Serializable pid, List<Role> parent);

	/**
	 * 根据uid查询角色
	 * 
	 * @param uid
	 * @return
	 */
	List<Role> listByUid(String uid);

	/**
	 * 保存RoleDto
	 * 
	 * @param entity
	 * @return
	 */
	boolean saveDto(RoleDto entity);

	/**
	 * 根据id修改RoleDto
	 * 
	 * @param entity
	 * @return
	 */
	boolean updateDtoById(RoleDto entity);
}
