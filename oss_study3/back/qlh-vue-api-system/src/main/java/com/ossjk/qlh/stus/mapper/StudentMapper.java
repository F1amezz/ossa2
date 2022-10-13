package com.ossjk.qlh.stus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.vo.StudentVO;
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
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select  id,name as value from student where name like #{name}")
    List<StudentVO> interactionStu(String name);


    @Update("update  student as stu join clazz as c on stu.cID = c.id set pwd = c.deftpwd where stu.id=#{id} ")
    int revise(String id);


    @Select("SELECT * FROM student  WHERE `name` LIKE #{name}")
    List<Student> istuscx(String name);

    @Select("select s.id  from student  s left join clazz c  on s.cid = c.id where c.name =#{cnm} and s.name=#{snm}")
    String findSidbyClzNm_Snm(@Param("cnm") String cnm, @Param("snm")String snm);

}
