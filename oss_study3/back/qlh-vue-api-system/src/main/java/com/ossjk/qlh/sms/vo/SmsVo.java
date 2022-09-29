package com.ossjk.qlh.sms.vo;

import com.ossjk.qlh.sms.entity.Sms;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 *
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.sms.vo
 * @ClassName: SmsVo
 * @Description: 站内信息Vo
 * @author: chair
 * @date: 2021年3月17日 下午10:00:19
 */
@Data
@ApiModel(value = "站内信息Vo")
public class SmsVo extends Sms {

    private String sname;

}
