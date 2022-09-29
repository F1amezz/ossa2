package com.ossjk.core.constant;

import java.io.File;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.constant
 * @ClassName: CacheConstant
 * @Description: 缓存常量
 * @author: chair
 * @date: 2021年3月7日 下午11:01:03
 */
public class CacheConstant {

	/**
	 * key分割符
	 */
	public static final String CACHE_SEPARATOR = ":";

	/**
	 * jwtToken前缀
	 */
	public static final String CACHE_JWT_TOKEN_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "jwt";

	/**
	 * user缓存前缀
	 */
	public static final String CACHE_VERIFYCODE_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "verifycode";
	/**
	 * 数据字典缓存前缀
	 */
	public static final String CACHE_DICTIONARY_DICTIONARY_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "dictionary" + CACHE_SEPARATOR + "dictionary";
	/**
	 * 系统设置缓存前缀
	 */
	public static final String CACHE_DICTIONARY_SYSTEMSETTING_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "dictionary" + CACHE_SEPARATOR + "systemsetting";
	/**
	 * email设置缓存前缀
	 */
	public static final String CACHE_DICTIONARY_EMAILSETTING_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "dictionary" + CACHE_SEPARATOR + "emailsetting";
	/**
	 * 查找密码邮件前缀
	 */
	public static final String CACHE_FIND_PWD_EMAIL_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "findPwdEmail";

	/**
	 * 选项字典缓存前缀
	 */
	public static final String CACHE_DICTIONARY_OPTIONDICTIONARY_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "dictionary" + CACHE_SEPARATOR + "optiondictionary";

	/**
	 * 地区前缀
	 */
	public static final String CACHE_AREA_PREFIX = "qlh" + CACHE_SEPARATOR + "sys" + CACHE_SEPARATOR + "area";

	/**
	 * 生成jwttoken缓存key<br>
	 * 前缀"+CACHE_SEPARATOR+"用户id"+CACHE_SEPARATOR+"token
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	public static String generateCacheJwtToken(String uid, String token) {
		return generateCacheJwtTokenPrefix(uid) + CACHE_SEPARATOR + token;
	}

	/**
	 * 生成jwttoken缓存key<br>
	 * 前缀"+CACHE_SEPARATOR+"用户id
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	public static String generateCacheJwtTokenPrefix(String uid) {
		return CACHE_JWT_TOKEN_PREFIX + CACHE_SEPARATOR + uid;
	}
}
