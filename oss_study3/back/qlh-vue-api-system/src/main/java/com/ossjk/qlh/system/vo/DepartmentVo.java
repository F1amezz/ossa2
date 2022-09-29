package com.ossjk.qlh.system.vo;

import java.util.List;

import com.ossjk.qlh.system.entity.Department;

import lombok.Data;

@Data
public class DepartmentVo extends Department {

	private List<Department> list;

}
