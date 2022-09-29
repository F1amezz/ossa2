package com.ossjk.qlh.stus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.stus.entity.Student;
import com.ossjk.qlh.stus.vo.StudentVO;
import org.apache.ibatis.annotations.Select;

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
public interface StudentMapper10 extends BaseMapper<Student> {

    @Select("select * from student where cid = #{cid}")
    List<Student> lookupClazzStu(String cid);

//    List<Student> mdPwd(Student student);
}
