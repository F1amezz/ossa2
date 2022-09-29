package com.ossjk.core.system.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.system.annotation.DbColumnMapper;
import com.ossjk.core.system.annotation.OptionDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.core.system.vo
 * @ClassName: LoginUser
 * @Description: 登录用户辅助类
 * @author: chair
 * @date: 2021年2月28日 下午9:10:57
 */
@Data
public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	private String id;


	@ApiModelProperty(value = "角色", required = false)
	private String sname;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String head;
	/**
	 * 登录名
	 */
	@ApiModelProperty(value = "登录名", required = true)
	private String lname;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 生日
	 */
	@ApiModelProperty(value = "生日")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date birth;
	/**
	 * 性别 1-男、2-女
	 */
	@OptionDictionary(key = "user-sex")
	@ApiModelProperty(value = "性别 1-男、2-女", required = true)
	private Integer sex;

	@ApiModelProperty(value = "职务：2-组长、1-班长", required = true)
	private Integer groupleader;


	/**
	 * 邮件
	 */
	@ApiModelProperty(value = "邮件")
	private String email;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机", required = true)
	private String mobile;



	@ApiModelProperty(value = "学校", required = false)
	private String school;



	/**
	 * 专业
	 */
	@ApiModelProperty(value = "专业")

	private String professional;

	/**
	 * 籍贯
	 */
	@ApiModelProperty(value = "籍贯")

	private String nativeplace;
	/**
	 * 报到时间
	 */
	@ApiModelProperty(value = "报到时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date cometime;

	/**
	 * 紧急联系人关系：1-父亲；2-母亲；3-兄弟；4-姐妹；5-亲戚；6-朋友
	 */
	@ApiModelProperty(value = "紧急联系人关系")
	private Integer relationship;

	@ApiModelProperty(value = "紧急联系人手机", required = true)
	private String rphone;
	/**
	 * 身份证
	 */
	@ApiModelProperty(value = "身份证")
	@TableField("idCard")
	private String idcard;

	/**
	 * 状态：1-在读；2-休学；3-毕业
	 */
	@ApiModelProperty(value = "状态：1-在读；2-休学；3-毕业")
	private Integer state;

	/**
	 * 班级id
	 */
	@ApiModelProperty(value = "班级id")
	@DbColumnMapper(columns = "name", tableName = "clazz", condition = "id")
	private String cid;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remarks;

	/**
	 * 角色名称
	 */
	private String rname;
	/**
	 * 部门名称
	 */
	private String dname ;
	/**
	 * 是否禁用：1-是、2-否
	 */
	@ApiModelProperty(value = "是否禁用：1-是、2-否", required = true)
	private Integer isdisable;






//-------------------------------------------------------------------------------------
	/**
	 * 部门标识包含子
	 */
	@ApiModelProperty(value = "部门标识包含子")
	private Set<String> departmentCodes;
	/**
	 * 角色标识包含子
	 */
	@ApiModelProperty(value = "角色标识包含子")
	private Set<String> roleCodes;

	/**
	 * 部门标识
	 */
	@ApiModelProperty(value = "部门标识")
	private Set<String> departmentCode;

	/**
	 * 角色标识
	 */
	@ApiModelProperty(value = "角色标识")
	private Set<String> roleCode;

	/**
	 * 权限标识
	 */
	@ApiModelProperty(value = "权限标识")
	private Set<String> permissionCode;

	/**
	 * 权限菜单
	 */
	@ApiModelProperty(value = "权限菜单")
	private List<PermissionMenu> permissionMenus;

}
