package com.ossjk.qlh.stus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.edu.entity.Clazz;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.vo.StudentVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Copyright  2022-06-08 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.stus.mapper
 * @ClassName: StudentMapper
 * @Description: -Mapper
 * @author: Rick.yang
 * @date:  2022-06-08 16:32:38 
 */
public interface StudentMapper5 extends BaseMapper<Student> {
    /*
    * 查看该班级学生
     */
    @Select("select * from student where cid = #{id}")
    List<Student> lookupClazzStu(String id);

    @Select("select name from student where cid = #{cid}")
    List<String> findClazzStuName(String cid);

    /*
     * 重置该班级学生密码
     */
    @Update("update student set pwd = #{deftpwd} where cid = #{id}")
    int updStuPwd(@Param("deftpwd") String deftpwd,@Param("id") String id);
}
