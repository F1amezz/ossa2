package com.ossjk.qlh.exams.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.exams.entity.*;
import com.ossjk.qlh.exams.service.IAnswersService;
import com.ossjk.qlh.exams.service.IQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright  2022-06-14 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.exams.controller
 * @ClassName: AnswersController
 * @Description: Answers-控制器
 * @author: 
 * @date:  2022-06-14 11:31:17 
 */
@Api(tags = "答卷（题）表")
@RestController
@RequestMapping("/exams/answers")
public class AnswersController extends BaseController {
	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private IAnswersService iAnswersService;

	@ApiOperation(value = "列表")
//	//@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Answers>> list(Page page) {
		System.out.println(page);
		System.out.println(page.toString());
		QueryWrapper<Answers> queryWrapper = new QueryWrapper<Answers>();
		return ResponseBean.Success(iAnswersService.page(page,queryWrapper));
	}
	@ApiOperation(value = "列表")
//	//@RequiresPermissions("")
	@GetMapping("/listById")
	public ResponseBean<Page<Answers>> listById(int current,int size,String kid) {
		Page<Answers> answersPage = new Page<>();
		answersPage.setSize(size);
		answersPage.setCurrent(current);
		QueryWrapper<Answers> queryWrapper = new QueryWrapper<Answers>();
		queryWrapper.eq("kid",kid);
		return ResponseBean.Success(iAnswersService.page(answersPage,queryWrapper));
	}

	@ApiOperation(value = "添加")
//	//@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Answers record) {
		if (iAnswersService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Answers> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Answers answers = iAnswersService.getById(id);
		if (ObjectUtil.isNotNull(answers)) {
			return ResponseBean.Success(answers);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	//@RequiresPermissions("")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Answers record) {
		if (iAnswersService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	//@RequiresPermissions("")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iAnswersService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}



	@ApiOperation(value = "查看答案。")
//	@RequiresPermissions("")
	@GetMapping("/getOneAnswers")
	public ResponseBean getOneAnswers(@ApiParam(value = "考试id") @RequestParam(name = "id", required = false)  String id,
									  @ApiParam(value = "expid") @RequestParam(name = "expid", required = false) String expid,
									  @ApiParam(value = "cid") @RequestParam(name = "cid", required = false) String cid,
									  @ApiParam(value = "stuid") @RequestParam(name = "stuid", required = false) String stuid) {

		QueryWrapper<Answers> answersQueryWrapper = new QueryWrapper<>();
		answersQueryWrapper.eq("sid",stuid).eq("expid",expid).eq("kid",id).eq("cid",cid);
		Answers answers = iAnswersService.getOne(answersQueryWrapper);

		return ResponseBean.Success(answers);
	}


	@ApiOperation(value = "自动判题。")
//	@RequiresPermissions("")
	@GetMapping("/autoCorrect")
	public ResponseBean autoCorrect(AutoCorrectArrayDto autoCorrectArrayDto) {

		ArrayList<Question> questions = new ArrayList<>();
		//选择题
		if (autoCorrectArrayDto.getChoiceQuestionId()!=null) {
			for (int i = 0; i < autoCorrectArrayDto.getChoiceQuestionId().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getChoiceQuestionId()[i]);
				if (question.getAnswer().equals(autoCorrectArrayDto.getChoiceQuestionAnswer()[i])) {
					question.setResult(1);

					question.setResultScore(autoCorrectArrayDto.getChoiceQuestionScore()[i]);
					question.setResultAnswer(autoCorrectArrayDto.getChoiceQuestionAnswer()[i]);

				} else {
					question.setResult(0);
					question.setResultScore(0);
					question.setResultAnswer(autoCorrectArrayDto.getChoiceQuestionAnswer()[i]);

				}
				questions.add(question);
			}

		}

		//多选题
		if (autoCorrectArrayDto.getMultipleChoiceQuestionsId()!=null) {
			for (int i = 0; i < autoCorrectArrayDto.getMultipleChoiceQuestionsId().length; i++) {

				Question question = iQuestionService.getById(autoCorrectArrayDto.getMultipleChoiceQuestionsId()[i]);
				if (question.getAnswer().equals(autoCorrectArrayDto.getMultipleChoiceQuestionsAnswer()[i])){
					question.setResult(1);
					question.setResultScore(autoCorrectArrayDto.getChoiceQuestionScore()[i]);
					question.setResultAnswer(autoCorrectArrayDto.getMultipleChoiceQuestionsAnswer()[i]);

				}else {
					question.setResult(0);
					question.setResultScore(0);
					question.setResultAnswer(autoCorrectArrayDto.getMultipleChoiceQuestionsAnswer()[i]);

				}
				questions.add(question);
			}
		}

		//判断题
		if (autoCorrectArrayDto.getTorFQuestionsId()!=null){
			for (int i = 0; i < autoCorrectArrayDto.getTorFQuestionsId().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getTorFQuestionsId()[i]);
				if (autoCorrectArrayDto.getTorFQuestionsAnswer()[i]==null){
					question.setResult(0);
					question.setResultScore(0);
				} else if (autoCorrectArrayDto.getTorFQuestionsAnswer()[i].equals("")) {
					question.setResult(0);
					question.setResultScore(0);
				} else {
					String flag;
					if (autoCorrectArrayDto.getTorFQuestionsAnswer()[i].equals("1")){
						flag="正确";
					}else if (autoCorrectArrayDto.getTorFQuestionsAnswer()[i].equals("5")){
						flag="未填";
					}else {
						flag="错误";
					}
					if (question.getAnswer().equals(flag)){
						question.setResult(1);
						question.setResultScore(autoCorrectArrayDto.getTorFQuestionsScore()[i]);
						question.setResultAnswer(flag);
					}else {
						question.setResult(0);
						question.setResultScore(0);
						question.setResultAnswer(flag);


					}
				}

				questions.add(question);
			}
		}


		/////////////////////////////
		//填空题

		if (autoCorrectArrayDto.getCompletion()!=null){
			for (int i = 0; i < autoCorrectArrayDto.getCompletion().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getCompletion()[i]);
				question.setResult(1);
				question.setResultAllScore(autoCorrectArrayDto.getCompletionScore()[i]);
				questions.add(question);
			}}
		//大题1

		if (autoCorrectArrayDto.getMajorTopic1()!=null){
			for (int i = 0; i < autoCorrectArrayDto.getMajorTopic1().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getMajorTopic1()[i]);
				question.setResultAllScore(autoCorrectArrayDto.getMajorTopic1Score()[i]);
				question.setResult(1);
				questions.add(question);
			}}
		//大题2

		if (autoCorrectArrayDto.getMajorTopic2()!=null){
			for (int i = 0; i < autoCorrectArrayDto.getMajorTopic2().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getMajorTopic2()[i]);
				question.setResultAllScore(autoCorrectArrayDto.getMajorTopic2Score()[i]);
				question.setResult(1);
				questions.add(question);
			}}
		//大题3

		if (autoCorrectArrayDto.getMajorTopic3()!=null){
			for (int i = 0; i < autoCorrectArrayDto.getMajorTopic3().length; i++) {
				Question question = iQuestionService.getById(autoCorrectArrayDto.getMajorTopic3()[i]);
				question.setResultAllScore(autoCorrectArrayDto.getMajorTopic3Score()[i]);
				question.setResult(1);
				questions.add(question);
			}}
		return ResponseBean.Success(questions);
	}

	@ApiOperation(value = "改卷提交。")
//	@RequiresPermissions("")
	@GetMapping("/markingSubmit")
	public ResponseBean markingSubmit(@RequestParam int score,@RequestParam String kid,@RequestParam String sid) {
		UpdateWrapper<Answers> answersUpdateWrapper = new UpdateWrapper<>();
		answersUpdateWrapper.set("score",score);
		answersUpdateWrapper.set("isjudged",1);
		answersUpdateWrapper.eq("kid",kid);
		answersUpdateWrapper.eq("sid",sid);
		boolean update = iAnswersService.update(null, answersUpdateWrapper);
		return ResponseBean.Success(update);
	}



	@ApiOperation(value = "出题次数。")
//	@RequiresPermissions("")
	@PostMapping("/addHadQuestions")
	@Transactional
	public ResponseBean addHadQuestions( @RequestBody QuestionsArray questions) {
		Answers byId = iAnswersService.getById(questions.getAnswersid());
		if (byId==null){
			return ResponseBean.Success("出题次数添加失败");
		} else if (byId.getScore()!=null) {
			return ResponseBean.Success("出题次数添加失败");
		}


		Iterator<Question> iterator = questions.getQuestions().iterator();


		while (iterator.hasNext()){
			Question next = iterator.next();
			if (next.getResult()==1){
				UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
				if (next.getExposeTimes()==null){next.setExposeTimes(0);}
				if (next.getRightTimes()==null){next.setRightTimes(0);}
				questionUpdateWrapper.eq("id",next.getId());
				questionUpdateWrapper.set("expose_times",next.getExposeTimes()+1);

				questionUpdateWrapper.set("right_times",next.getRightTimes()+1);
				iQuestionService.update(null,questionUpdateWrapper);
			}else {

				UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
				questionUpdateWrapper.eq("id",next.getId());
				if (next.getExposeTimes()==null){next.setExposeTimes(0);}
				questionUpdateWrapper.set("expose_times",next.getExposeTimes()+1);

				iQuestionService.update(null,questionUpdateWrapper);
			}
		}




		return ResponseBean.Success("ok");
	}

}
