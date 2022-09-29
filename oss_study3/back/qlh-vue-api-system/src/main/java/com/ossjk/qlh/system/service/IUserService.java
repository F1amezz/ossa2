package com.ossjk.qlh.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.system.dto.UserDto;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.vo.User2Vo;
import com.ossjk.qlh.system.vo.UserVo;
import com.ossjk.qlh.system.vo.UserinDeptVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.system.service
 * @ClassName: UserService
 * @Description: 用户表-服务类接口
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
public interface IUserService extends IService<User> {

    boolean saveDto(UserDto dto);

    boolean updateDtoById(UserDto dto);

    Page pageVo(Page page, Wrapper wrapper);

    UserVo getVoById(Serializable id);

    String getIDByRcode( String rcode);

    String getMgrIDByYzID( String id);

    List<User2Vo> getListVuser(  QueryWrapper<User2Vo> queryWrapper );

    List<UserVo> getListVuserInUse(   );

    Map<String,List<User2Vo>> getVuserInRole( );

    List<UserinDeptVo> selectVoByDptName(String dname);

    List<UserinDeptVo> selectVoByDptName();

}
