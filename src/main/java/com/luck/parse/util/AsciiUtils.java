package com.luck.parse.util;

import com.luck.parse.domain.CarMessage;
import com.luck.parse.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author 张梦娇
 * @description <p>将报文转为bean对象</p>
 * @date 2023-08-23 14:54
 **/
@Component
public class AsciiUtils {


    @Autowired
    private static CarService carService;

    /**
     * 将报文转换成ascii码值
     *
     * @param hexString
     * @return
     */
    public static String hexToAscii(String hexString) {

        /*车辆传输的报文 有状态的信息 在这里 我们将报文里的/去除 进行转换 转换完成之后 我们进行截取  得到新的解析后的报文  然后根据不同的位数
         *将车辆报文信息放在里边*/
        long l = System.currentTimeMillis();
//        System.err.println("正在解析哦~~~~~~~~~~~~~~" + l / 1000);
//        System.err.println(hexString);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
//        System.err.println("转码完成啦~~~~~~~~~~~~~~~~~~~~~~~" + (System.currentTimeMillis() - l));
        return output.toString();
    }


    /**
     * 报文解析bean对象
     *
     * @param message
     * @return
     */
    public static CarMessage toBean(String message) {
//        System.err.println("bean" + message);
        //切割车辆报文 将报文信息 放入bean对象
        CarMessage carMessage = new CarMessage();
        carMessage.setStartPlace(message.substring(0, 1));
        carMessage.setMessageId(message.substring(1, 13));
        carMessage.setVin(message.substring(13, 30));
        carMessage.setLongitude(Double.valueOf(message.substring(30, 41)));
        carMessage.setLatitude(Double.valueOf(message.substring(41, 51)));
        carMessage.setSpeed(Double.valueOf(message.substring(51, 57)));
        carMessage.setSumMileage(Double.valueOf(message.substring(57, 68)));
        carMessage.setSumVoltage(Double.valueOf(message.substring(68, 74)));
        carMessage.setSumElectricity(Double.valueOf(message.substring(74, 79)));
        carMessage.setInsulationResistance(Double.valueOf(message.substring(79, 88)));
        carMessage.setTapPosition(message.substring(88, 89));
        carMessage.setAcceleratePedalJourney(Double.valueOf(message.substring(89, 91)));
        carMessage.setBrakingPedalJourney(Double.valueOf(message.substring(91, 93)));
        carMessage.setFuelRate(Double.valueOf(message.substring(93, 98)));
        carMessage.setMotorControllerTemperature(Double.valueOf(message.substring(98, 104)));
        carMessage.setMotorSpeed(Double.valueOf(message.substring(104, 109)));
        carMessage.setMotorTorque(Double.valueOf(message.substring(109, 113)));
        carMessage.setMotorTemperature(Double.valueOf(message.substring(113, 119)));
        carMessage.setMotorVoltage(Double.valueOf(message.substring(119, 124)));
        carMessage.setMotorCurrent(Double.valueOf(message.substring(124, 132)));
        carMessage.setPowerBatteryResidue(Double.valueOf(message.substring(132, 138)));
        carMessage.setAtPresentStateAllowMaximumFeedbackPower(Double.valueOf(message.substring(138, 144)));
        carMessage.setAtPresentStateAllowMaximumDischargingPower(Double.valueOf(message.substring(144, 150)));
        carMessage.setSelfCheckingCountBMS(Integer.valueOf(message.substring(150, 152)));
        carMessage.setChargingAndDischargingCurrentPowerBattery(Double.valueOf(message.substring(152, 157)));
        carMessage.setTotalVoltageLoadEndPowerBatteryV3(Double.valueOf(message.substring(157, 163)));
        carMessage.setSingleMaximumVoltage(Double.valueOf(message.substring(163, 167)));
        carMessage.setMinimumVoltageBattery(Double.valueOf(message.substring(167, 171)));
        carMessage.setMaximumBatteryTemperature(Double.valueOf(message.substring(171, 177)));
        carMessage.setMinimumBatteryTemperature(Double.valueOf(message.substring(177, 183)));
        carMessage.setPowerBatteryAvailableCapacity(Double.valueOf(message.substring(183, 189)));
        carMessage.setVehicleState(Integer.valueOf(message.substring(189, 190)));
        carMessage.setChargingState(Integer.valueOf(message.substring(190, 191)));
        carMessage.setRunningState(Integer.valueOf(message.substring(191, 192)));
        carMessage.setSOC(Integer.valueOf(message.substring(192, 193)));
        carMessage.setWorkingStateRechargeableEnergyStorageDevice(Integer.valueOf(message.substring(193, 194)));
        carMessage.setDriveMotorCondition(Integer.valueOf(message.substring(194, 195)));
        carMessage.setWhetherState(Integer.valueOf(message.substring(195, 196)));
        carMessage.setEAS(Integer.valueOf(message.substring(196, 197)));
        carMessage.setPTC(Integer.valueOf(message.substring(197, 198)));
        carMessage.setEPS(Integer.valueOf(message.substring(198, 199)));
        carMessage.setABS(Integer.valueOf(message.substring(199, 200)));
        carMessage.setMCU(Integer.valueOf(message.substring(200, 201)));
        carMessage.setPowerBatteryHeatingState(Integer.valueOf(message.substring(201, 202)));
        carMessage.setPowerBatteryNowState(Integer.valueOf(message.substring(202, 203)));
        carMessage.setPowerBatteryKeepWarmState(Integer.valueOf(message.substring(203, 204)));
        carMessage.setDCDC(Integer.valueOf(message.substring(204, 205)));
        carMessage.setCHG(Integer.valueOf(message.substring(205, 206)));
        carMessage.setVerify(message.substring(206, 207));
        carMessage.setEndPlace(message.substring(207, 208));
//        selectCarEnterprise(carMessage.getVin());
        return carMessage;
    }




}








