package com.ossjk.qlh.system.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ossjk.qlh.system.vo.User2Vo;
import com.ossjk.qlh.system.vo.UserVo;
import com.ossjk.qlh.system.vo.UserinDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.system.dto.UserDto;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.entity.UserRole;
import com.ossjk.qlh.system.mapper.UserMapper;
import com.ossjk.qlh.system.service.IUserRoleService;
import com.ossjk.qlh.system.service.IUserService;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Autowired
	private IUserRoleService iUserRoleService;

	@Override
	public List<UserVo> getListVuserInUse( ){
		return this.baseMapper.getAllVuser();
	}

	@Override
	public boolean saveDto(UserDto dto) {
		boolean result = save(dto);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uid", dto.getId());
		iUserRoleService.remove(queryWrapper);
		UserRole entity = new UserRole();
		entity.setUid(dto.getId());
		entity.setRid(dto.getRoleId());
		iUserRoleService.save(entity);
		return result;
	}

	@Override
	public boolean updateDtoById(UserDto dto) {
		boolean result = updateById(dto);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uid", dto.getId());
		iUserRoleService.remove(queryWrapper);
		UserRole entity = new UserRole();
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
	public UserVo getVoById(Serializable id) {
		return this.baseMapper.selectVoById(id);
	}

	public String getIDByRcode( String rcode){
		return baseMapper. getIDByRcode(  rcode);
	}

	@Override
	public String getMgrIDByYzID( String id){
		return baseMapper.getMgrIDByYzID(  id);
	}

	@Override
	public List<User2Vo> getListVuser(QueryWrapper<User2Vo> queryWrapper ){
		return baseMapper.getAllByRcode(  queryWrapper);
	}
	//根据岗位取员工信息，进行分组
	@Override
	public Map<String,List<User2Vo>> getVuserInRole( ){
		QueryWrapper<User2Vo> queryWrapper = new QueryWrapper();
		queryWrapper.like("code","zx%");
		queryWrapper.or().like("code","zm%");
		Map<String,List<User2Vo>> map = new HashMap();
		List<User2Vo> zx =  baseMapper.getAllByRcode(  queryWrapper);
		if(zx!=null && zx.size()>0){
			map.put("zx",zx);
		}
		queryWrapper.clear();
		queryWrapper.like("code","qd%");
		List<User2Vo> qd =  baseMapper.getAllByRcode(  queryWrapper);
		if(qd!=null && qd.size()>0){
			map.put("qd",qd);
		}

		queryWrapper.clear();
		queryWrapper.like("code","dy%");
		map.put("dy",baseMapper.getAllByRcode(  queryWrapper));

		queryWrapper.clear();
		queryWrapper.like("code","tr%");
		queryWrapper.or().eq("code","mgr2");
		map.put("tecr",baseMapper.getAllByRcode(  queryWrapper));



		return map;
	}

	@Override
	public List<UserinDeptVo> selectVoByDptName(String dname){
		return this.baseMapper.selectVoByDptNm(dname);
	}

	@Override
	public List<UserinDeptVo> selectVoByDptName(){
		return this.baseMapper.selectAllVo(   );
	}

}
