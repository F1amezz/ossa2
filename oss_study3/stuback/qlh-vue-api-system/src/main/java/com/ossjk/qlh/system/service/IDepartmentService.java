package com.ossjk.qlh.system.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.entity.Department;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: DepartmentService
 * @Description: 部门表-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:30
 */
public interface IDepartmentService extends IService<Department> {

	/**
	 * 根据id删除子节点（包含自身）
	 * 
	 * @param id
	 * @return
	 */
	boolean removeChildrenById(Serializable id);

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
	List<Department> listChildrenById(Serializable id);

	/**
	 * 查找所有子节点（不包含自身）
	 * 
	 * @param pid
	 * @param parent
	 * @return
	 */
	List<Department> listChildrenByPid(Serializable pid, List<Department> parent);

	/**
	 * 根据uid查询部门
	 * 
	 * @param uid
	 * @return
	 */
	List<Department> listByUid(String uid);
}
