package com.ossjk.qlh.interview.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.interview.dto.InterviewDTO;
import com.ossjk.qlh.interview.entity.Company;
import com.ossjk.qlh.interview.entity.Interview;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.service.IInterviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.controller
 * @ClassName: MianshiController
 * @Description: Mianshi-控制器
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@Api(tags = "面试表")
@RestController
@RequestMapping("/interview/mianshi")
public class InterviewController extends BaseController {
	
	@Autowired
	private IInterviewService iMianshiService;
	@Autowired
	private ISystemCommonApi systemCommonApi;

	@ApiOperation(value = "列表")
	//@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Interview>> list(Page page,HttpServletRequest request) {
		//查看自己
		String token = this.getToekn(request);
		LoginUser me = systemCommonApi.getCacheLoginUser(token);

		QueryWrapper<Interview> queryWrapper = new QueryWrapper<Interview>();
		queryWrapper.eq("stuid",me.getId());

		return ResponseBean.Success(iMianshiService.page(page,queryWrapper));
	}

	@ApiOperation(value = "添加")
	//@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Interview record) {
		if (iMianshiService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "添加DTO")
	//@RequiresPermissions("")
	@PostMapping("/saveDTO")
	public ResponseBean saveDTO(@RequestBody InterviewDTO dto ,HttpServletRequest request ) {
		String token = this.getToekn(request);
		LoginUser me = systemCommonApi.getCacheLoginUser(token);
		System.out.println(dto);
		dto.getIntv().setStuid(me.getId());

		 if (iMianshiService.saveDTO(dto.getIntv(),dto.getCmp(),dto.getPost())) {
			return ResponseBean.Success();
		 } else {
		 	return ResponseBean.Fail();
		 }
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Interview> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Interview interview = iMianshiService.getById(id);
		if (ObjectUtil.isNotNull(interview)) {
			return ResponseBean.Success(interview);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "总结")
	//@RequiresPermissions("")
	@PutMapping("/zhjie")
	public ResponseBean 总结(@RequestBody Interview record) {

		Interview org = 	iMianshiService.getById(record.getId());

		org.setMslast(record.getMslast());
		org.setMsresult(record.getMsresult());
		org.setMsscore(record.getMsscore());
		org.setMszbqk(record.getMszbqk());
		org.setMszj(record.getMszj());


		org.setExam(record.getExam());

		if (iMianshiService.updateById(org)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "点评")
	//@RequiresPermissions("")
	@PutMapping("/msdp")
	public ResponseBean 点评(@RequestBody Interview record, HttpServletRequest request ) {
		//去redis取得当前用户
		LoginUser loginUser = systemCommonApi.getLoginUser(getToekn(request));
		Interview org = 	iMianshiService.getById(record.getId());
		org.setMsdp( "<p>"+  loginUser.getName()+" [ "+ StringUtil.smt6.format(new Date())  +" ]: </p>" +  record.getMsdp());

		if (iMianshiService.updateById(org)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	//@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Interview record) {
		if (iMianshiService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	//@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iMianshiService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
