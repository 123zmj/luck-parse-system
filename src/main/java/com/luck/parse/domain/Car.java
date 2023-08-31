package com.luck.parse.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 张梦娇
 * @description <p>车辆实体类</p>
 * @date 2023-08-26 14:49
 **/
@Data
@ToString
public class Car {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 车辆 vin
     */
    private String vin;
    /**
     * 车牌号
     */
    private String licensePlate;
    /**
     * 车型
     */
    private String model;
    /**
     * 车辆品牌
     */
    private String brand;
    /**
     * 录入时间
     */
    private Date createTime;
    /**
     * 录入人姓名
     */
    private String createBy;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 驾驶证
     */
    private String driverLicense;
    /**
     * 住址
     */
    private String address;
    /**
     * 车辆载客
     */
    private Integer passengerCapacity;
    /**
     * 车辆上线状态
     */
    private Integer onlineStatus;
    /**
     * 企业id
     */
    private Integer enterpriseId;
    /**
     * 电子围栏 id
     */
    private Integer fenceId;
    /**
     * 车辆规则id
     */
    private Integer vehicleRulesId;

}
