package com.luck.parse.domain;

import lombok.Data;

/**
 * @author 张梦娇
 * @description <p>车辆规则</p>
 * @date 2023-08-25 10:02
 **/
@Data
public class VehicleRules {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 车辆最高总电压
     */
    private Double maxTotalVoltage;

    /**
     * 车辆最大总电流
     */
    private Double maxTotalCurrent;

    /**
     * 最小绝缘电阻
     */
    private Double minResistance;
    /**
     * 最大速度
     */
    private Double maxSpeed;
    /**
     * 电机控制器温度最低温
     */
    private Double minTemperature;
    /**
     * 电机控制器温度最高温
     */
    private Double maxTemperature;

    /**
     * 电机最高温度
     */
    private Double maxMotorTemperature;
    /**
     * 电机最低温度
     */
    private Double minMotorTemperature;

    /**
     * 电机额定转速
     */
    private Double ratedSpeed;
    /**
     * 电机最大转矩
     */
    private Double maxTorque;
    /**
     * 电机最小转矩
     */
    private Double minTorque;
    /**
     * 电机最高电压
     */
    private Double maxVoltage;
    /**
     * 电机最低电压
     */
    private Double minVoltage;
    /**
     * 电机最大电流
     */
    private Double maxMotorCurrent;
    /**
     * 电机最小电流
     */
    private Double minMotorCurrent;
    /**
     * 动力电池剩余电量SOC最低电量
     */
    private Double minBatteryCapacity;
    /**
     * 车辆允许的最大反馈功率
     */
    private Double vehicleMaxFeedbackPower;
    /**
     * 车辆允许的最大放电功率
     */
    private Double vehicleMaxDischargePower;
    /**
     * 最大燃耗率
     */
    private Double maxFuelConsumptionRate;
    /**
     * 动力电池充放电电流最小值
     */
    private Double minChargeDischargeCurrent;
    /**
     * 动力电池充放电电流最大值
     */
    private Double maxChargeDischargeCurrent;
    /**
     * 动力电池负载端总电压V3最小值
     */
    private Double minLoadEndTotalVoltage;
    /**
     * 动力电池负载端总电压V3最大值
     */
    private Double maxLoadEndTotalVoltage;
    /**
     * 动力电池最小可用容量
     */
    private Double minAvailableCapacity;
    /**
     * 规则状态 1、可用 2、不可用
     */
    private Integer state;
    /**
     * 企业id
     */
    private Integer enterpriseId;

}
