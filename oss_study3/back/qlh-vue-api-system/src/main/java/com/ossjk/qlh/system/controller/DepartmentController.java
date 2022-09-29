package com.ossjk.qlh.system.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.service.IDepartmentService;
import com.ossjk.qlh.system.service.IThirdaccountService;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.system.vo.UserinDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright 2020-12-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.controller
 * @ClassName: DepartmentController
 * @Description: 部门表-控制器
 * @author: chair
 * @date: 2020-12-14 22:06:29
 */
@Api(tags = "部门表")
@LogModule(value = "部门管理")
@RestController
@RequestMapping("/system/department")
public class DepartmentController extends BaseController {

	@Autowired
	private IDepartmentService iDepartmentService;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;

	@LogOperation("列表")
	@ApiOperation(value = "列表")
	@RequiresPermissions(value = { "00000005" })
	@GetMapping("/list")
	public ResponseBean<List<Department>> list() {
		return ResponseBean.Success(iDepartmentService.list(new QueryWrapper<Department>().orderByAsc("level","sort")));
	}

	@ApiOperation(value = "部门员工列表")
	@GetMapping("/deptEmps")
	public ResponseBean<Map> lists(@ApiParam(value = "部门名称")    @RequestParam(name = "dept",required = false)   String dept, HttpServletRequest request) {
		//封装数据
		Map data = new HashMap();
		//  没参数普通与员工默认自己本月的，主管  ：本部门本月的
		//自己
		LoginUser usr =iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
		String role = usr.getRoleCode().iterator().next();
		String roleFlag = role.substring(0,2);
		List<UserinDeptVo> usrs = new ArrayList<>();

		if("mg".equals(roleFlag) ||"ad".equals(roleFlag)   ) {  //部门负责人
			//部门内的员工导航
			Page page2 = new Page();
			page2.setSize(100L);

			if (roleFlag.equals("ad")) { // 总经办：boss
				//出现部门导航   二级部门
				data.put("dpts", departmentService.list(new QueryWrapper<Department>().orderByAsc("sort")));
			}
			data.put("emps", iUserService.pageVo(page2, new QueryWrapper<User>().eq("d.name", usr.getDname())));

			//本部门
			if (StrUtil.isNotBlank(dept)) {
				if (!"靖凯开源".equals(dept)) {
					// 加载员工s
					usrs = iUserService.selectVoByDptName(dept);
				} else {
					usrs = iUserService.selectVoByDptName();
				}
			} else {
				if (roleFlag.equals("ad")) { // 总经办：boss
					usrs = iUserService.selectVoByDptName();
				}else{
					usrs = iUserService.selectVoByDptName(usr.getDname());
				}
			}
			UserinDeptVo all = new UserinDeptVo();
			all.setId("全部人");
			all.setName("全部人");
			usrs.add(0, all);

			data.put("emps", usrs);
		}

		return ResponseBean.Success(data);
	}


	@LogOperation("添加")
	@ApiOperation(value = "添加")
	@RequiresPermissions(value = { "000000050005" })
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Department recode) {
		// 防止部门编码重复
		Department department = iDepartmentService.getOne(new QueryWrapper<Department>().eq("code", recode.getCode()));
		if (ObjectUtil.isNotEmpty(department)) {
			return ResponseBean.Fail("部门标识	重复");
		}
		if (iDepartmentService.save(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("去编辑")
	@ApiOperation(value = "去编辑")
	@RequiresPermissions(value = { "000000050010" })
	@GetMapping("/toUpdate")
	public ResponseBean<Department> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Department department = iDepartmentService.getById(id);
		if (ObjectUtil.isNotEmpty(department)) {
			return ResponseBean.Success(department);
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("编辑")
	@ApiOperation(value = "编辑")
	@RequiresPermissions(value = { "000000050010" })
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Department recode) {
		if (iDepartmentService.updateById(recode)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@LogOperation("删除")
	@ApiOperation(value = "删除")
	@RequiresPermissions(value = { "000000050015" })
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iDepartmentService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
