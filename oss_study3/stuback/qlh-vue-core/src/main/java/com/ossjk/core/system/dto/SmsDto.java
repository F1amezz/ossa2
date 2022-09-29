package com.ossjk.core.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.core.system.dto
 * @ClassName: SmsDto
 * @Description: 站内信息数据
 * @author: chair
 * @date: 2021/3/12 14:58
 */
@ApiModel(value = "站内信息数据")
@Data
public class SmsDto {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题", required = true)
	private String title;
	/**
	 * 发送者id
	 */
	@ApiModelProperty(value = "发送者id", required = true)
	private String sid;
	/**
	 * 接收者id
	 */
	@ApiModelProperty(value = "接收者id", required = true)
	private String rid;
	/**
	 * 阅读状态:1-未读、2-已读
	 */
	@ApiModelProperty(value = "阅读状态:1-未读、2-已读", required = true)
	private Integer rstate;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 内容级别：1-普通 2-一般 3-紧急
	 */
	@ApiModelProperty(value = "内容级别：1-普通 2-一般 3-紧急", required = true)
	private Integer level;
	/**
	 * 内容类型：1-消息、2-通知、3-公告、4-其他
	 */
	@ApiModelProperty(value = "内容类型：1-消息、2-通知、3-公告、4-其他", required = true)
	private Integer type;
	/**
	 * 发送时间
	 */
	@ApiModelProperty(value = "发送时间", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date stime;
}
