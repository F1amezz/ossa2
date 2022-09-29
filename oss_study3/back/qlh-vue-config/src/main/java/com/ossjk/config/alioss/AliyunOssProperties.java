package com.ossjk.config.alioss;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.config.alioss
 * @ClassName: AliyunOssProperties
 * @Description: 阿里云Oss配置属性
 * @author: chair
 * @date: 2021/10/6 23:50
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliyunOssProperties {
	/**
	 * 访问身份验证中用到用户标识
	 */
	private String endpoint;
	/**
	 * 用户用于加密签名字符串和oss用来验证签名字符串的密钥
	 */
	private String accessKeyId;
	/**
	 * oss对外服务的访问域名
	 */
	private String accessKeySecret;
	/**
	 * oss的存储空间
	 */
	private String bucketName;
}
