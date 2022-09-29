package com.ossjk.qlh.interview.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.interview.entity.Posts;
import com.ossjk.qlh.interview.service.IPostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Copyright  2022-06-06 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study3.controller
 * @ClassName: PostsController
 * @Description: Posts-控制器
 * @author: Rick.yang
 * @date:  2022-06-06 14:48:32 
 */
@Api(tags = "岗位表")
@RestController
@RequestMapping("/interview/posts")
public class PostsController extends BaseController {
	
	@Autowired
	private IPostsService iPostsService;

	@ApiOperation(value = "列表")
	//@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Posts>> list(Page page, @ApiParam(value = "公司名称") @RequestParam(name = "cname",required = false) String cname,
                                          @ApiParam(value = "岗位名称") @RequestParam(name = "pname",required = false) String pname,
                                          @ApiParam(value = "状态:全部，有效，失效") @RequestParam(name = "state",required = false) String state) {

		QueryWrapper<Posts> queryWrapper = new QueryWrapper<Posts>();


		  if( StrUtil.isNotBlank(cname)    ){
			  queryWrapper.like("c.name","%"+cname+"%");
		  }

		  if( StrUtil.isNotBlank(pname)    ){
			   queryWrapper.like("p.name","%"+pname+"%");
		   }

			if( StrUtil.isNotBlank(state) && !("全部".equals(state)) ){
			Date now = null;
			try {
				now = StringUtil.smt2.parse(StringUtil.smt2.format(new Date()));

				if("失效".equals(state)){
					//有效     bgntm >= now
					//queryWrapper.ge("bgntm",now);
					//   endtm <= now
					queryWrapper.le("p.endtm",now);
				}else{
					//有效     endtm>=now
					queryWrapper.gt("p.endtm",now);
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return ResponseBean.Success(iPostsService.selectVO(page,queryWrapper));
	}

	@ApiOperation(value = "列出一个公司的招聘岗位")
	//@RequiresPermissions("")
	@GetMapping("/listByCid")
	public ResponseBean<List<Posts>> listByCmpyid(@ApiParam(value = "公司id",required = true) @RequestParam(name = "cmpid",required = true) String cmpid) {
		QueryWrapper<Posts> queryWrapper = new QueryWrapper<Posts>();
		queryWrapper.eq("company",cmpid);
		return ResponseBean.Success(iPostsService.list(queryWrapper));
	}


	@ApiOperation(value = "添加")
	//@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Posts record) {
		if (iPostsService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Posts> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Posts posts = iPostsService.getById(id);
		if (ObjectUtil.isNotNull(posts)) {
			return ResponseBean.Success(posts);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	//@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Posts record) {
		if (iPostsService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	//@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iPostsService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}
