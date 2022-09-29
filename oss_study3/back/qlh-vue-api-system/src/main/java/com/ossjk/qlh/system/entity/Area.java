package com.ossjk.qlh.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
 * Copyright  2021-03-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.system.entity
 * @ClassName: Area
 * @Description: 地区表-实体类
 * @author: Jason
 * @date:  2021-03-08 10:31:26 
 */
@ApiModel(value = "地区表")
public class Area extends BaseEntity<Area> {

	private static final long serialVersionUID = 1L;

	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码", required = true)
	@TableId("id")
	private String id;
	/**
	 * 区域名称
	 */
	@ApiModelProperty(value = "区域名称")
	@TableField("name")
	private String name;
	/**
	 * 区域上级标识
	 */
	@ApiModelProperty(value = "区域上级标识")
	@TableField("pid")
	private Integer pid;
	/**
	 * 地名简称
	 */
	@ApiModelProperty(value = "地名简称")
	@TableField("simplename")
	private String simplename;
	/**
	 * 区域等级
	 */
	@ApiModelProperty(value = "区域等级")
	@TableField("level")
	private Integer level;
	/**
	 * 城市编码
	 */
	@ApiModelProperty(value = "城市编码")
	@TableField("citycode")
	private String citycode;
	/**
	 * 邮政编码
	 */
	@ApiModelProperty(value = "邮政编码")
	@TableField("zipcode")
	private String zipcode;
	/**
	 * 组合名称
	 */
	@ApiModelProperty(value = "组合名称")
	@TableField("mername")
	private String mername;
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	@TableField("lng")
	private Float lng;
	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	@TableField("lat")
	private Float lat;
	/**
	 * 拼音
	 */
	@ApiModelProperty(value = "拼音")
	@TableField("pinyin")
	private String pinyin;
	 
	public String  getId() {
		return id;
	}
	public void  setId(String id) {
		this.id = id;
	}
	public String  getName() {
		return name;
	}
	public void  setName(String name) {
		this.name = name;
	}
	public Integer  getPid() {
		return pid;
	}
	public void  setPid(Integer pid) {
		this.pid = pid;
	}
	public String  getSimplename() {
		return simplename;
	}
	public void  setSimplename(String simplename) {
		this.simplename = simplename;
	}
	public Integer  getLevel() {
		return level;
	}
	public void  setLevel(Integer level) {
		this.level = level;
	}
	public String  getCitycode() {
		return citycode;
	}
	public void  setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String  getZipcode() {
		return zipcode;
	}
	public void  setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String  getMername() {
		return mername;
	}
	public void  setMername(String mername) {
		this.mername = mername;
	}
	public Float  getLng() {
		return lng;
	}
	public void  setLng(Float lng) {
		this.lng = lng;
	}
	public Float  getLat() {
		return lat;
	}
	public void  setLat(Float lat) {
		this.lat = lat;
	}
	public String  getPinyin() {
		return pinyin;
	}
	public void  setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	 
	 
	@Override
	protected Serializable pkVal() {
		return null;
	}

	@Override
	public String toString() {
		return "Area ["
	+"id=" + id +","
	+"name=" + name +","
	+"pid=" + pid +","
	+"simplename=" + simplename +","
	+"level=" + level +","
	+"citycode=" + citycode +","
	+"zipcode=" + zipcode +","
	+"mername=" + mername +","
	+"lng=" + lng +","
	+"lat=" + lat +","
	+"pinyin=" + pinyin 
	+ "]";
	}

	 
}
