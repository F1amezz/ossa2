package com.ossjk.config.jwttoken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ossjk.config.mvc.ResourceMappersProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.jwttoken
 * @ClassName: JwtTokenUtil
 * @Description: JwtToken工具类
 * @author: chair
 * @date: 2021年2月24日 上午11:55:09
 */
@Configurable
@Component
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenUtil {

	@Autowired
	private JwtProperties jwtProperties;
	private static final String CLAIM_KEY_UID = "sub";
	private static final String CLAIM_KEY_CREATED = "created";

	/**
	 * 根据负责生成JWT的token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()).compact();
	}

	/**
	 * 从token中获取JWT中的负载
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.info("JWT格式验证失败:{}", token);
		}
		return claims;
	}

	/**
	 * 生成token的过期时间
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + jwtProperties.getDuration());
	}

	/**
	 * 从token中获取登录用户名
	 */
	public String getUserIdFromToken(String token) {
		String uid = null;
		try {
			Claims claims = getClaimsFromToken(token);
			uid = claims.getSubject();
		} catch (Exception e) {
		}
		return uid;
	}

	/**
	 * 验证token是否还有效
	 *
	 * @param token       客户端传入的token
	 */
	public boolean validateToken(String token, String uid) {
		String guid = getUserIdFromToken(token);
		return guid.equals(uid) && !isTokenExpired(token);
	}

	/**
	 * 判断token是否已经失效
	 */
	public boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	/**
	 * 从token中获取过期时间
	 */
	private Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 根据用户信息生成token
	 */
	public String generateToken(String uid) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_UID, uid);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 判断token是否可以被刷新
	 */
	public boolean canRefresh(String token) {
		return (getExpiredDateFromToken(token).getTime() - new Date().getTime()) <= jwtProperties.getFlush();
	}

	/**
	 * 刷新token
	 */
	public String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 从token中获取过期时间
	 */
	public long getExpiredMilliseconFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return (claims.getExpiration().getTime() - System.currentTimeMillis());
	}

	/**
	 * 获取时效
	 */
	public Long getDuration() {
		return jwtProperties.getDuration();
	}

	/**
	 * 刷新时间
	 */
	public Long getFlush() {
		return jwtProperties.getFlush();
	}
}
