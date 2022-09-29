package com.ossjk.qlh.system.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.dto.UserDto;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.entity.UserRole;
import com.ossjk.qlh.system.service.IThirdaccountService;
import com.ossjk.qlh.system.service.IUserRoleService;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.system.vo.User2Vo;
import com.ossjk.qlh.system.vo.UserToUpdateVo;
import com.ossjk.qlh.system.vo.UserVo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: UserController
 * @Description: 用户表-控制器
 * @author: chair
 * @date: 2020-12-14 22:06:31
 */
@Api(tags = "用户表")
@LogModule(value = "用户管理")
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private IThirdaccountService iThirdaccountService;

    @LogOperation("列表")
    @ApiOperation(value = "列表")
    @RequiresPermissions(value = { "00000000" })
    @GetMapping("/list")
    public ResponseBean<Page<UserVo>> list(
            @ApiParam(value = "名字") @RequestParam(name = "name", required = false) String name,
            @ApiParam(value = "登录名") @RequestParam(name = "lname", required = false) String lname,
            @ApiParam(value = "手机") @RequestParam(name = "mobile", required = false) String mobile,
            @ApiParam(value = "邮件") @RequestParam(name = "email", required = false) String email,
            @ApiParam(value = "部门id") @RequestParam(name = "did", required = false) String did,
            @ApiParam(value = "角色id") @RequestParam(name = "rid", required = false) String rid, Page page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        if (StrUtil.isNotBlank(lname)) {
            queryWrapper.like("lname", lname);
        }
        if (StrUtil.isNotBlank(mobile)) {
            queryWrapper.like("mobile", mobile);
        }
        if (StrUtil.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        if (StrUtil.isNotBlank(did)) {
            queryWrapper.like("d.id", did);
        }
        if (StrUtil.isNotBlank(rid)) {
            queryWrapper.like("r.id", rid);
        }
        queryWrapper.eq("isdisable", 2);
        queryWrapper.orderByDesc("crtm");
        return ResponseBean.Success(iUserService.pageVo(page, queryWrapper));
    }

    @LogOperation("列表")
    @ApiOperation(value = "列表")
    // @RequiresPermissions(value = { "00000000" })
    @GetMapping("/list2")
    public ResponseBean<Page<UserVo>> list2(Page page) {
        QueryWrapper<UserVo> queryWrapper = new QueryWrapper<UserVo>();
        queryWrapper.eq("r.name", "老师");
        return ResponseBean.Success(iUserService.pageVo(page, queryWrapper));
    }

    @LogOperation("员工列表")
    @ApiOperation(value = "员工列表")
    @GetMapping("/listAll")
    public ResponseBean<Map<String, List<User2Vo>>> listAlls() {

        return ResponseBean.Success(iUserService.getVuserInRole());
    }

    @LogOperation("全体员工列表")
    @ApiOperation(value = "全体员工列表")
    @GetMapping("/listEmps")
    public ResponseBean<List<User2Vo>> listAllEmps() {
        return ResponseBean.Success(iUserService.getListVuser(new QueryWrapper<User2Vo>()));
    }

    @LogOperation("添加")
    @ApiOperation(value = "添加")
    @RequiresPermissions(value = { "000000000005" })
    @PostMapping("/save")
    public ResponseBean save(@RequestBody UserDto recode) {
        recode.setPwd(SecureUtil.md5(Constant.USER_DEFUALT_PWD));
        recode.setIsdisable(2);
        if (iUserService.saveDto(recode)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @LogOperation("去编辑")
    @ApiOperation(value = "去编辑")
    @RequiresPermissions(value = { "000000000020" })
    @GetMapping("/toUpdate")
    public ResponseBean<UserToUpdateVo> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
        User user = iUserService.getById(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", user.getId());
        UserRole userRole = iUserRoleService.getOne(queryWrapper);

        UserToUpdateVo userToUpdateVo = new UserToUpdateVo();
        userToUpdateVo.setUser(user);
        userToUpdateVo.setUserRole(userRole);
        if (ObjectUtil.isNotNull(user)) {
            return ResponseBean.Success(userToUpdateVo);
        } else {
            return ResponseBean.Fail();
        }
    }

    @LogOperation("编辑")
    @ApiOperation(value = "编辑")
    @RequiresPermissions(value = { "000000000010" })
    @PutMapping("/update")
    public ResponseBean update(@RequestBody UserDto recode) {
        if (iUserService.updateDtoById(recode)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @LogOperation("删除")
    @ApiOperation(value = "删除")
    @RequiresPermissions(value = { "000000000015" })
    @DeleteMapping("/remove")
    public ResponseBean delete(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iUserService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @LogOperation("离职")
    @ApiOperation(value = "离职")
    @RequiresPermissions(value = { "000000000025" })
    @DeleteMapping("/leave")
    public ResponseBean leave(@ApiParam(value = "id") @RequestParam(name = "id") String id) {

        User user = iUserService.getById(id);
        user.setIsdisable(1);
        user.setPwd("SDS@!E@#$@KLJ:LJKYIUJI");
        user.setEmail(null);

        user.setRemarks(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "->离职");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", user.getId());
        iThirdaccountService.remove(queryWrapper);

        if (iUserService.updateById(user)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }

    }
}
