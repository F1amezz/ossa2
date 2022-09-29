package com.ossjk.qlh.system.service;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Stu {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Excel(name="姓名")    //注释类型?   元数据 meta data
    private String name;

    @Excel(name="手机号码")
    private String mobile;

}
