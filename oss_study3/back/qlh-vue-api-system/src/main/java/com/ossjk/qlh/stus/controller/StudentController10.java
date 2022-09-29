package com.ossjk.qlh.stus.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.service.IStudentService;
import com.ossjk.qlh.stus.service.IStudentService10;
import com.ossjk.qlh.stus.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
public class StudentController10 extends BaseController {
	
	@Autowired
	private IStudentService10 iStudentService10;

//	@ApiOperation(value = "查询班级学生")
//	@GetMapping("/toStu")
//	public ResponseBean<List<Student>> list3(Student student) {
//		QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
//		return ResponseBean.Success(iStudentService10.lookupClazzStu(student));
//	}

	@ApiOperation(value = "查询班级学生")
	@GetMapping("/toStu")
	public ResponseBean<List<Student>> list3(String cid) {
		QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
		return ResponseBean.Success(iStudentService10.lookupClazzStu(cid));
	}

	public List<Student> lookupClazzStu(String cid){
		QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
		return iStudentService10.lookupClazzStu(cid);
	}

//	@ApiOperation(value = "列表")
//	@RequiresPermissions("")
//	@GetMapping("/list")
//	public ResponseBean<Page<Student>> list(Page page) {
//		QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
//		return ResponseBean.Success(iStudentService.page(page,queryWrapper));
//	}
//
//
//	@ApiOperation(value = "感应查询学生")
//	@GetMapping("/intnStu")
//	public ResponseBean<List<StudentVO>> list2( @ApiParam(value = "name") @RequestParam(name = "name",required = false)  String name) {
//		return ResponseBean.Success(iStudentService.interactionStu(name) );
//	}
//
//	@ApiOperation(value = "添加")
//	@RequiresPermissions("")
//	@PostMapping("/save")
//	public ResponseBean save(@RequestBody Student record) {
//		if (iStudentService.save(record)) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//	@ApiOperation(value = "去编辑")
//	@RequiresPermissions("")
//	@GetMapping("/toUpdate")
//	public ResponseBean<Student> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
//		Student student = iStudentService.getById(id);
//		if (ObjectUtil.isNotNull(student)) {
//			return ResponseBean.Success(student);
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//
//	@ApiOperation(value = "编辑")
//	@RequiresPermissions("")
//	@PutMapping("/update")
//	public ResponseBean update(@RequestBody Student record) {
//		if (iStudentService.updateById(record)) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}
//
//	@ApiOperation(value = "删除")
////	@RequiresPermissions("")
//	@DeleteMapping("/remove")
//	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
//		if (iStudentService.removeByIds(Arrays.asList(ids))) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
//	}

}
