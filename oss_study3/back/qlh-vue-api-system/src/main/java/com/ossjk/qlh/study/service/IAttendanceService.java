package com.ossjk.qlh.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.study.entity.Attendance;

import java.io.File;
import java.util.List;

/**
 * Copyright  2022-09-27 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.study.service
 * @ClassName: AttendanceService
 * @Description:  -服务类接口
 * @author: flame
 * @date:  2022-09-27 16:21:34 
 */
public interface IAttendanceService extends IService<Attendance> {

    List<Attendance> parseAttendance(File file);
}
