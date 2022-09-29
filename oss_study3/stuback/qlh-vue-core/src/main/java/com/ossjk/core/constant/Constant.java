package com.ossjk.core.constant;

import org.springframework.stereotype.Component;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.constant
 * @ClassName: Constant
 * @Description: 常量类
 * @author: chair
 * @date: 2020年12月8日 下午2:40:00
 */
public class Constant {

	/**
	 * 请求token
	 */
	public static final String REQUEST_HEADER_TOKEN = "jwttoken";
	/**
	 * request作用域-uid
	 */
	public static final String REQUEST_ATTRIBUTE_UID = "uid";
	/**
	 * request作用域-登录对象
	 */
	public static final String REQUEST_ATTRIBUTE_LOGIN_USER = "loginUser";
	/**
	 * request作用域-部门标识包含子
	 */
	public static final String REQUEST_ATTRIBUTE_DEPARTMENT_CODES = "departmentCodes";
	/**
	 * request作用域-角色标识包含子
	 */
	public static final String REQUEST_ATTRIBUTE_ROLE_CODES = "roleCodes";
	/**
	 * request作用域-部门标识
	 */
	public static final String REQUEST_ATTRIBUTE_DEPARTMENT_CODE = "departmentCode";
	/**
	 * request作用域-角色标识
	 */
	public static final String REQUEST_ATTRIBUTE_ROLE_CODE = "roleCode";
	/**
	 * request作用域-权限标识
	 */
	public static final String REQUEST_ATTRIBUTE_PERMISSION_CODE = "permissionCode";
	/**
	 * 用户默认密码
	 */
	public static final String USER_DEFUALT_PWD = "888888";
	/**
	 * 成功返回码
	 */
	public static final int RESPONSE_CODE_SUCCESS = 1000;
	/**
	 * 请求失败返回码
	 */
	public static final int RESPONSE_CODE_FAIL = 1001;
	/**
	 * 请求抛出异常返回码
	 */
	public static final int RESPONSE_CODE_EXCEPTION = 1002;
	/**
	 * 未登陆状态返回码
	 */
	public static final int RESPONSE_CODE_NOLOGIN = 1003;
	/**
	 * 无操作权限返回码
	 */
	public static final int RESPONSE_CODE_NOAUTH = 1004;
	/**
	 * 可能在别的地方登录
	 */
	public static final int RESPONSE_CODE_BADTOKEN = 1005;
	/**
	 * 刷新Token
	 */
	public static final int RESPONSE_CODE_REFRESH = 1006;
	/**
	 * 成功返回信息
	 */
	public static final String RESPONSE_MSG_SUCCESS = "操作成功";
	/**
	 * 成功返回信息
	 */
	public static final String RESPONSE_MSG_FAIL = "操作失败";
	/**
	 * 请求抛出异常返回信息
	 */
	public static final String RESPONSE_MSG_EXCEPTION = "后台异常，请联系管理员";
	/**
	 * 未登陆状态返回信息
	 */
	public static final String RESPONSE_MSG_NOLOGIN = "请登录后再操作";
	/**
	 * token被删除了
	 */
	public static final String RESPONSE_MSG_BADTOKEN = "登录失效了，请重新登录。";
	/**
	 * 无操作权限返回信息
	 */
	public static final String RESPONSE_MSG_NOAUTH = "没有权限操作";
	/**
	 * 刷新Token返回信息
	 */
	public static final String RESPONSE_MSG_REFRESH = "刷新token";

}
