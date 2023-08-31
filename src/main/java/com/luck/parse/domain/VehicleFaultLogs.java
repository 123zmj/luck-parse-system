package com.luck.parse.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 张梦娇
 * @description <p>故障报警记录</p>
 * @date 2023-08-29 19:52
 **/
@Data
@ToString
public class VehicleFaultLogs {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 故障码
     */
    private String faultCode;
    /**
     * 车辆vin
     */
    private String vin;
    /**
     * 开始报警时间
     */
    private Date startTime;
    /**
     * 解决时间
     */
    private String resolveTime;

}
