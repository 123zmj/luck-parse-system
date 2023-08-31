package com.luck.parse.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 张梦娇
 * @description <p>车辆报文bean对象</p>
 * @date 2023-08-22 16:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarMessage implements Serializable {
    /**
     * 起始位
     */
    private String startPlace;
    /**
     * 消息标识
     */
    private String messageId;
    /**
     * 车辆vin
     */
    private String vin;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 车速
     */
    private Double speed;
    /**
     * 总里程
     */
    private Double sumMileage;
    /**
     * 总电压
     */
    private Double sumVoltage;
    /**
     * 总电流
     */
    private Double sumElectricity;
    /**
     * 绝缘电阻
     */
    private Double insulationResistance;
    /**
     * 档位
     */
    private String tapPosition;
    /**
     * 加速踏板行程值
     */
    private Double acceleratePedalJourney;
    /**
     * 制动踏板行程值
     */
    private Double brakingPedalJourney;
    /**
     * 燃料消耗率
     */
    private Double fuelRate;
    /**
     * 电机控制器温度
     */
    private Double motorControllerTemperature;
    /**
     * 电机转速
     */
    private Double motorSpeed;
    /**
     * 电机转矩
     */
    private Double motorTorque;
    /**
     * 电机温度
     */
    private Double motorTemperature;
    /**
     * 电机电压
     */
    private Double motorVoltage;
    /**
     * 电机电流
     */
    private Double motorCurrent;
    /**
     * 动力电池剩余电量SOC
     */
    private Double powerBatteryResidue;
    /**
     * 当前状态允许的最大反馈功率
     */
    private Double atPresentStateAllowMaximumFeedbackPower;
    /**
     * 当前状态允许的最大放电功率
     */
    private Double atPresentStateAllowMaximumDischargingPower;
    /**
     * BMS自检计数器
     */
    private Integer selfCheckingCountBMS;
    /**
     * 动力电池充放电电流
     */
    private Double chargingAndDischargingCurrentPowerBattery;
    /**
     * 动力电池负载端总电压V3
     */
    private Double TotalVoltageLoadEndPowerBatteryV3;
    /**
     * 单次最大电压
     */
    private Double singleMaximumVoltage;
    /**
     * 单体电池最低电压
     */
    private Double minimumVoltageBattery;
    /**
     * 单体电池最高温度
     */
    private Double maximumBatteryTemperature;
    /**
     * 单体电池最低温度
     */
    private Double minimumBatteryTemperature;
    /**
     * 动力电池可用容量
     */
    private Double powerBatteryAvailableCapacity;
    /**
     * 车辆状态
     */
    private Integer vehicleState;
    /**
     * 充电状态
     */
    private Integer chargingState;
    /**
     * 运行状态
     */
    private Integer runningState;
    /**
     * SOC
     */
    private Integer SOC;
    /**
     *可充电储能装置工作状态
     */
    private Integer workingStateRechargeableEnergyStorageDevice;
    /**
     * 驱动电机状态
     */
    private Integer driveMotorCondition;
    /**
     * 定位是否有效
     */
    private Integer whetherState;
    /**
     * EAS
     */
    private Integer EAS;
    /**
     * PTC
     */
    private Integer PTC;
    /**
     * EPS
     */
    private Integer EPS;
    /**
     * ABS
     */
    private Integer ABS;
    /**
     * MCU
     */
    private Integer MCU;
    /**
     * 动力电池加热状态
     */
    private Integer powerBatteryHeatingState;
    /**
     * 动力电池当前状态
     */
    private Integer powerBatteryNowState;
    /**
     * 动力电池保温状态
     */
    private Integer powerBatteryKeepWarmState;
    /**
     * DCDC
     */
    private Integer DCDC;
    /**
     * CHG
     */
    private Integer CHG;
    /**
     * 校验位
     */
    private String verify;
    /**
     * 截止位
     */
    private String endPlace;
}
