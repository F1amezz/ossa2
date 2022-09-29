package com.ossjk.core.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.base.entity
 * @ClassName: BaseEntity 
 * @Description: 基础实体类
 * @author: chair
 * @date: 2020年12月14日 下午11:17:34
 */
public abstract class BaseEntity<T extends Model> extends Model<BaseEntity<T>> {

	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	protected String id;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(value = "crtm", fill = FieldFill.INSERT)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	protected Date crtm;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(value = "mdtm", fill = FieldFill.INSERT_UPDATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	protected Date mdtm;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@TableField(value = "crer", fill = FieldFill.INSERT)
	protected String crer;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	@TableField(value = "mder", fill = FieldFill.INSERT_UPDATE)
	protected String mder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCrtm() {
		return crtm;
	}

	public void setCrtm(Date crtm) {
		this.crtm = crtm;
	}

	public Date getMdtm() {
		return mdtm;
	}

	public void setMdtm(Date mdtm) {
		this.mdtm = mdtm;
	}

	public String getCrer() {
		return crer;
	}

	public void setCrer(String crer) {
		this.crer = crer;
	}

	public String getMder() {
		return mder;
	}

	public void setMder(String mder) {
		this.mder = mder;
	}


}
