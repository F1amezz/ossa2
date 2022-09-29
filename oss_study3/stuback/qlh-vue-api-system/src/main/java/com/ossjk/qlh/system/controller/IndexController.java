package com.ossjk.qlh.system.controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.ossjk.core.system.dto.*;
import com.ossjk.qlh.system.dto.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.config.redis.RedisUtil;
import com.ossjk.config.wechat.WechatOAuth2Util;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.IAreaCommonApi;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Thirdaccount;
import com.ossjk.qlh.system.entity.Student;
import com.ossjk.qlh.system.service.IThirdaccountService;
import com.ossjk.qlh.system.service.IStudentService;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;

@Api(tags = "index")
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private IAreaCommonApi iAreaCommonApi;
	@Autowired
	private IStudentService iUserService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private WechatOAuth2Util wechatOAuth2Util;
	@Autowired
	private IThirdaccountService iThirdaccountService;


	@ApiOperation(value = "登录")
	@PostMapping("/login")
	@ResponseBody
	public ResponseBean<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
		// 从redis获取验证码
		String code = (String) redisUtil.get(CacheConstant.CACHE_VERIFYCODE_PREFIX + CacheConstant.CACHE_SEPARATOR + loginDto.getKey());
		System.out.println(code);
		//if (StrUtil.equals(loginDto.getCode(), code)) {
			return iSystemCommonApi.login(loginDto.getName(), loginDto.getPwd());
	    //	} else {
		//	return ResponseBean.Fail("验证码错误。");
		//}
	}


	@LogOperation("编辑")
	@RequiresPermissions(value = { "00000030" })
	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody SettingDto settingDto) {
		if (iSystemCommonApi.updateSystemSetting(BeanUtil.copyProperties(settingDto, SystemSetting.class)) & iSystemCommonApi.updateEmailSetting(BeanUtil.copyProperties(settingDto, EmailSetting.class))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	@LogOperation("获取用户信息")
	@ApiOperation(value = "获取用户信息")
	@GetMapping("/getInfo")
	@ResponseBody
	public ResponseBean<LoginUser> getInfo(HttpServletRequest request) {
		return ResponseBean.Success(iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request)));
	}



	@LogOperation("登出")
	@ApiOperation(value = "登出")
	@GetMapping("/logout")
	@ResponseBody
	public ResponseBean logout(HttpServletRequest request) {
		iSystemCommonApi.removeCacheLoginUserByToken(this.getToekn(request));
		return ResponseBean.Success("注销成功。");
	}

	@LogOperation("发送忘记密码邮件")
	@ApiOperation(value = "发送忘记密码邮件")
	@GetMapping("/sendFindPwdEmail")
	@ResponseBody
	public ResponseBean sendFindPwdEmail(String email) {
		// 是否禁用：1-是、2-否
		Student user = iUserService.getOne(new QueryWrapper<Student>().eq("email", email).eq("isdisable", "2"));
		if (ObjectUtil.isNotEmpty(user)) {
			String code = RandomUtil.randomString(6);
			redisUtil.set(CacheConstant.CACHE_FIND_PWD_EMAIL_PREFIX + CacheConstant.CACHE_SEPARATOR + email, code, 70);
			iSystemCommonApi.sendEmail(user.getEmail(), "【QLH】忘记密码", "尊敬的" + user.getLname() + "，您好！\r\n" + "\r\n" + "您的验证码为" + code + "，请于1分钟内正确输入验证码，如非本人操作，请忽略。", false);
			return ResponseBean.Success("邮件已发送。");
		} else {
			return ResponseBean.Fail("找不到绑定该邮件的帐号,或该帐号被禁用。");
		}

	}

	@LogOperation("找回密码")
	@ApiOperation(value = "找回密码")
	@PutMapping("/findPwd")
	@ResponseBody
	public ResponseBean findPwd(@RequestBody FindPwdDto findPwdDto) {
		// 尝试获取邮件的验证码
		String redisCode = (String) redisUtil.get(CacheConstant.CACHE_FIND_PWD_EMAIL_PREFIX + CacheConstant.CACHE_SEPARATOR + findPwdDto.getEmail());
		if (StrUtil.isEmpty(redisCode)) {
			return ResponseBean.Fail("验证码超时了。");
		}
		if (!StrUtil.equals(redisCode, findPwdDto.getCode())) {
			return ResponseBean.Fail("验证码校验失败，请检查。");
		}
		// 查找绑定的用户
		// 是否禁用：1-是、2-否
		Student user = iUserService.getOne(new QueryWrapper<Student>().eq("email", findPwdDto.getEmail()).eq("isdisable", "2"));
		user.setPwd(SecureUtil.md5(findPwdDto.getNewPwd()));
		iUserService.updateById(user);
		return ResponseBean.Success("修改成功。");
	}

	/**
	 * 获取系统配置
	 * 
	 * @return
	 */
	@LogOperation("获取系统配置")
	@ApiOperation(value = "获取系统配置")
	@GetMapping("/getSystemSetting")
	@ResponseBody
	public ResponseBean<SystemSetting> getSystemSetting() {
		return ResponseBean.Success(iSystemCommonApi.getSystemSetting());
	}

	/**
	 * 获取所有地区
	 *
	 * @return
	 */
	@ApiOperation(value = "获取所有地区")
	@GetMapping("/getAllAreas")
	@ResponseBody
	public ResponseBean<List<AreaDto>> getAllAreas() {
		return ResponseBean.Success(iAreaCommonApi.getAllAreas());
	}

	@LogOperation("获取验证码")
	@ApiOperation(value = "获取验证码")
	@GetMapping("/getVerifyCode")
	@ResponseBody
	public ResponseBean<String> getVerifyCode(String key) {
		// 定义图形验证码的长、宽、验证码字符数、干扰线宽度
		ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(90, 40, 4, 4);
		// ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
		String code = captcha.getCode();
		// redis设置验证码，时效5分钟
		redisUtil.set(CacheConstant.CACHE_VERIFYCODE_PREFIX + CacheConstant.CACHE_SEPARATOR + key, code, 60 * 5);
		// 图形验证码写出，可以写出到文件，也可以写出到流
		return ResponseBean.Success(Constant.RESPONSE_MSG_SUCCESS, captcha.getImageBase64());
	}

	/**
	 * 获取微信登录信息
	 *
	 * @return
	 */
	@ApiOperation(value = "获取微信登录信息")
	@GetMapping("/getWechatLoginInfo")
	@ResponseBody
	public ResponseBean<WechatLoginInfo> getWechatLoginInfo() {
		WechatLoginInfo wechatLoginInfo = BeanUtil.copyProperties(wechatOAuth2Util.getWechatOpenProperties(), WechatLoginInfo.class);
		return ResponseBean.Success(wechatLoginInfo);
	}

	/**
	 * 微信回调接口
	 *
	 * @return
	 */
	@ApiOperation(value = "微信回调接口")
	@GetMapping("/wechatLogin")
	public String wechatLogin(String code, String state) throws WxErrorException {
		WxOAuth2AccessToken accessToken = wechatOAuth2Util.getAccessToken(code);
		String wxUid = accessToken.getUnionId();
		Thirdaccount thirdaccount = iThirdaccountService.getOne(new QueryWrapper<Thirdaccount>().eq("type", 1).eq("openid", wxUid));
		Student user = thirdaccount == null ? null : iUserService.getById(thirdaccount.getUid());
		if (ObjectUtil.isNotEmpty(thirdaccount) && ObjectUtil.isNotEmpty(user)) {
			// 判断帐号是否禁用
			if (ObjectUtil.equal(user.getIsdisable(), 1)) {
				String value = URLEncoder.createQuery().encode("帐号禁用。", Charset.forName("utf8"));
				return "redirect:" + String.format(wechatOAuth2Util.getWechatOpenProperties().getFailCallBackUri(), value);
			}
			// 生成token
			String token = iSystemCommonApi.generateToken(thirdaccount.getUid());
			// 存放token信息到redis
			iSystemCommonApi.getCacheLoginUser(token);
			return "redirect:" + String.format(wechatOAuth2Util.getWechatOpenProperties().getWebCallBackUri(), token);
		} else {
			return "redirect:" + String.format(wechatOAuth2Util.getWechatOpenProperties().getBingCallBackUri(), wxUid);
		}
	}

	/**
	 * 微信账号绑定
	 *
	 * @return
	 */
	@ApiOperation(value = "微信账号绑定")
	@PostMapping("/wechatBind")
	@ResponseBody
	public ResponseBean<String> bindUser(@RequestBody com.ossjk.core.system.dto.WechatBindDto wechatBindDto) {
		// 校验帐号信息是否正确
		String name = wechatBindDto.getName();
		String pwd = wechatBindDto.getPwd();
		Student user = iUserService.getOne(new QueryWrapper<Student>().and(wrapper -> {
			wrapper.eq("lname", name).or().eq("mobile", name).or().eq("email", name);
		}).eq("pwd", SecureUtil.md5(pwd)).eq("isdisable", 2));
		if (ObjectUtil.isNotEmpty(user)) {
			// 帐号没有问题。在第三方表新增。
			if (iThirdaccountService.saveWechatBind(user.getId(), wechatBindDto.getUnionid()) ) {
				return ResponseBean.Success("绑定成功。");
			} else {
				return ResponseBean.Fail("绑定失败,请联系管理员。");
			}

		} else {
			return ResponseBean.Fail("绑定失败,请检查账号。");
		}

	}
}
