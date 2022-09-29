package com.ossjk.qlh.schedule.taskjob;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.schedule.taskjob
 * @ClassName: TestJob 
 * @Description: 测试定时器
 * @author: chair
 * @date: 2021年8月10日 上午9:34:51
 */
@Component("testJob")
@Slf4j
public class TestJob {
	
	public void test(String params) {
		log.info("测试定时器。");
	}
	
}
