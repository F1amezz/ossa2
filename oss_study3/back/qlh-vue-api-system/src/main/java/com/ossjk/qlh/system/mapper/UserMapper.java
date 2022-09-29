package com.ossjk.qlh.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.qlh.system.vo.User2Vo;
import com.ossjk.qlh.system.vo.UserVo;
import com.ossjk.qlh.system.vo.UserinDeptVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.system.entity.User;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.mapper
 * @ClassName: UserMapper
 * @Description: 用户表-Mapper
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
public interface UserMapper extends BaseMapper<User> {

	Page selectPageVo(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);

    UserVo selectVoById( @Param("id")Serializable id);

    @Select("select id from  v_user where  code = #{rcode} ")
    String getIDByRcode(@Param("rcode")String rcode);

    //查询员工的部门领导
    @Select("select id from v_user where ( dcode =  (select SUBSTRING(code,1,length(code)-2) as dcode from v_user a  where a.id = #{id})) and code like 'ad%' ")
    String getMgrIDByYzID(@Param("id")String id);

    @Select("select id,name from  v_user   ${ew.customSqlSegment}  ")
    List<User2Vo> getAllByRcode(@Param("ew") QueryWrapper<User2Vo> queryWrapper);//  @Param("rcode")String rcode
    //@Select("select * from  v_user  where sidx>0 ")
    @Select("select * from  v_user ")
    List<UserVo> getAllVuser( );

    @Select("SELECT id,NAME FROM v_user where dname = #{dname} ")
    List<UserinDeptVo> selectVoByDptNm(@Param("dname") String dname);

    @Select("SELECT id,NAME FROM v_user ")
    List<UserinDeptVo> selectAllVo ( );


}
