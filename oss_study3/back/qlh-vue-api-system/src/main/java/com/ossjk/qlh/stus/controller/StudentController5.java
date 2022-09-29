package com.ossjk.qlh.stus.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService;
import com.ossjk.qlh.stus.service.IStudentService5;
import com.ossjk.qlh.stus.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.stus.controller
 * @ClassName: StudentController
 * @Description: -控制器
 * @author: Rick.yang
 * @date:  2022-06-08 16:32:38 
 */
@Api(tags = "学生信息")
@RestController
@RequestMapping("/stus/student")
public class StudentController5 extends BaseController {
	
	@Autowired
	private IStudentService5 iStudentService5;

	@ApiOperation(value = "查询该班级学生")
	@GetMapping("/toStus")
	public ResponseBean<List<Student>> toStus(String id) {
		return ResponseBean.Success(iStudentService5.lookupClazzStu(id));
	}

	@ApiOperation(value = "查询该班级学生name")
	@GetMapping("/stuInClass")
	public ResponseBean<List<String>>findClazzStuName(String cid){
		return ResponseBean.Success(iStudentService5.findClazzStuName(cid));

	}

	@ApiOperation(value = "修改全班学生默认密码")
	@RequiresPermissions("003000150025")
//	@RequestMapping("/updStuPwd")
	@GetMapping("/updStuPwd")
	public ResponseBean updPwd(@ApiParam(value = "班级默认密码") @RequestParam(name = "deftpwd") String deftpwd,
								  @ApiParam(value = "班级id") @RequestParam(name = "id") String id) {
		if (iStudentService5.updStuPwd(deftpwd,id) > 0) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

}
