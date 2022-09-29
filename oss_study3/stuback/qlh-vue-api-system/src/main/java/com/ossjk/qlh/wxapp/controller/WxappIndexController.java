package com.ossjk.qlh.wxapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ossjk.config.utils.JsonUtils;
import com.ossjk.config.wxminiapp.WxMaConfiguration;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.WechatBindDto;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api(tags = "微信小程序登录")
//@RestController
//@RequestMapping("/weapp")
public class WxappIndexController extends BaseController {

    //@Autowired
    private ISystemCommonApi iSystemCommonApi;


   // @Autowired
    private IStudentService iUserService;
    /**
     * 登陆接口
     */
    @ApiOperation(value = "登陆接口")
    @GetMapping("/getSessionInfo")
    public ResponseBean login(@RequestParam(name = "appid")String appid, @RequestParam(name = "code")String code) {

        System.out.println("====================getSessionInfo=================");
        if (StringUtils.isBlank(code)) {
            return  ResponseBean.Fail("empty jscode");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return ResponseBean.Success(session);
        } catch (WxErrorException e) {
            return  ResponseBean.Fail(e.toString());
        }
    }


        /**
         * <pre>
         * 获取用户信息接口
         * </pre>
         */
    @ApiOperation(value = "获取用户信息接口")
    @GetMapping("/getWxUserInfo")
    public ResponseBean getWxUserInfo(@ApiParam(value = "appid") @RequestParam(name = "appid")String appid,
                                      @ApiParam(value = "sessionKey") @RequestParam(name = "sessionKey")String sessionKey,
                                      @ApiParam(value = "signature") @RequestParam(name = "signature")String signature,
                                      @ApiParam(value = "rawData") @RequestParam(name = "rawData")String rawData,
                                      @ApiParam(value = "encryptedData") @RequestParam(name = "encryptedData")String encryptedData,
                                      @ApiParam(value = "iv") @RequestParam(name = "iv") String iv) {

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return  ResponseBean.Fail("user check failed");
        }

        // 解密用户信息
        WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        return ResponseBean.Success(wxUserInfo);

    }

    @ApiOperation(value = "小程序登录")
    @PostMapping("/wxLogin")
    @ResponseBody
    public ResponseBean login(@RequestBody WechatBindDto wechatBindDto) {
            return   iSystemCommonApi.login(wechatBindDto);
    }

    @ApiOperation(value = "微信-账号绑定")
    @PostMapping("/wxBind")
    @ResponseBody
    public ResponseBean wxBind(@RequestBody WechatBindDto wechatBindDto) {
        return iSystemCommonApi.wxBind(wechatBindDto);
    }

    @LogOperation("获取用户信息")
	@ApiOperation(value = "获取用户信息")
	@PostMapping("/getInfo")
	@ResponseBody
	public ResponseBean<Object> getXlstudentInfo(HttpServletRequest request) {
		return ResponseBean.Success(iSystemCommonApi.refreshTokenAndCacheLoginUser(this.getToekn(request)));
	}

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
