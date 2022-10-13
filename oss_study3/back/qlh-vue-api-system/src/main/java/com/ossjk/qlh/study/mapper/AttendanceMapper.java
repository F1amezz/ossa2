package com.ossjk.qlh.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.study.dto.AttendanceDTO;
import com.ossjk.qlh.study.entity.Attendance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Copyright  2022-09-27 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study.mapper
 * @ClassName: AttendanceMapper
 * @Description: -Mapper
 * @author: flame
 * @date:  2022-09-27 16:21:34 
 */
public interface AttendanceMapper extends BaseMapper<Attendance> {

    @Select("select a.*,s.`name` as stuname,c.name as clzname from attendance a  " +
            "  left join student  s on a.stuid = s.id left join clazz   c  on s.cid = c.id   " +
            "  where left(a.kqdate,7) =#{mth} and s.id=#{sid} ")
    List<AttendanceDTO> findStuDtoByMth(@Param("mth") String mth, @Param("sid")String sid   );

}
