package com.luck.parse.consumer;

import com.alibaba.fastjson.JSONObject;
import com.luck.parse.constants.LuckConstants;
import com.luck.parse.domain.Car;
import com.luck.parse.domain.CarMessage;
import com.luck.parse.domain.VehicleRules;
import com.luck.parse.service.CarService;
import com.luck.parse.sync.RunSyncRules;
import com.luck.parse.util.AsciiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 张梦娇
 * @description <p>接收报文并解析</p>
 * @date 2023-08-22 16:54
 **/
@Component
@Slf4j
public class ConsumerMessage {
    public static Map<String, CarMessage> messageMap = new HashMap<String, CarMessage>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CarService carService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 初始化线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    /**
     * kafka消费者 完成消息的解析
     *
     * @param record
     * @param ack
     */
    @KafkaListener(topics = {"beijing", "shanghai"}, groupId = "111")
    @Payload(required = false)
    public void kafkaListenerBJ(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        executorService.submit(() -> {
            messageHandling(record);
        });
        ack.acknowledge();
        //输出解析后的bean对象
//        System.out.println(carMessage);
//        ack.acknowledge();
    }


    public void messageHandling(ConsumerRecord<?, ?> record) {
//        System.out.println("--------------------------------信息来了---------------------------------------------------");
        log.info("信息来了：" + record.topic() + ":" + record.value());
//        System.out.println("来自："+record.topic()+record.partition()+"的报文："+System.currentTimeMillis() + record.value());
        long l = System.currentTimeMillis();
//        System.err.println("开始解析啦~~~~~~~~~~~~~~" + (l / 1000));
        String msg = String.valueOf(record.value()).replaceAll(" ", "");

        String asciiMessage = AsciiUtils.hexToAscii(msg);
//        获取转码后的报文bean对象
        CarMessage carMessage = AsciiUtils.toBean(asciiMessage);
/*//        将报文bean对象存入缓存redis
//        校验redis里有没有这个key vin 有就填进去 没有就创建一个新的
        if (redisTemplate.hasKey(carMessage.getVin())){
//            redisTemplate
        }*/
//        将获得的bean对象 通过mq发送给mq消费者 消费者根据自己不同的功能对报文bean对象进行分析判断
//        TODO 实时数据
        rabbitTemplate.convertAndSend(LuckConstants.CAR_MESSAGE_REAL_TIME_MQ, JSONObject.toJSONString(carMessage));
//        TODO hbase落库
        rabbitTemplate.convertAndSend(LuckConstants.CAR_MESSAGE_HBASE_MQ, JSONObject.toJSONString(carMessage));
//        故障记录  发送的是转码后的字符串 因为要去校验故障位 如过传对象的话 要加多余的判断 来校验故障出现在哪里
//        我们在传过来的报文这里 给报文末尾加上 时间戳 这样可以得到报文被kafka消费的时间
        rabbitTemplate.convertAndSend(LuckConstants.CAR_MESSAGE_FAULT_MQ, asciiMessage+System.currentTimeMillis());
        // TODO 电子围栏校验
        rabbitTemplate.convertAndSend(LuckConstants.CAR_MESSAGE_FENCE_MQ, JSONObject.toJSONString(carMessage));



/*
        //将车辆报文实时信息存入内存
//        判断内存里有没有这个车的报文
        if (messageMap.containsKey(carMessage.getVin())) {
//            有继续覆盖
            messageMap.put(carMessage.getVin(), carMessage);
        } else {
//            没有
            messageMap.put(carMessage.getVin(), carMessage);
        }*/


        //车辆报文落库hbase MQ发送


//        System.err.println("解析花费总时长：" + (System.currentTimeMillis() - l));
        Car car = carService.selectCarByVin(carMessage.getVin());
//        System.err.println(car);
        //根据车辆绑定的规则id 进行规则的校验
//        checkRules(car.getVehicleRulesId(), carMessage);
//        根据车辆绑定的电子围栏 进行围栏的校验

    }

    public static String checkRules(Integer rulesId, CarMessage carMessage) {
        //获取所有规则
        List<VehicleRules> vehicleRules = RunSyncRules.rulesMap.get(LuckConstants.RULE_MAP);
        for (VehicleRules vehicleRule : vehicleRules) {
            if (vehicleRule.getId() == rulesId) {
//             得到车辆绑定的规则进行校验
                if (vehicleRule.getMaxTotalVoltage() > carMessage.getSumVoltage()) {
//                    车辆行驶电压小于车辆允许的最大总电压
                    log.info("行驶电压正常");
                } else {
                    log.error("行驶电压异常");
                }

//                车辆最大总电流
                if (vehicleRule.getMaxTotalCurrent() > carMessage.getSumElectricity()) {
                    log.info("行驶电流正常");
                } else {
                    log.error("行驶电流异常");
                }
//                最小绝缘电阻
                if (vehicleRule.getMinResistance() < carMessage.getInsulationResistance()) {
                    log.info("行驶绝缘电阻正常");
                } else {
                    log.error("行驶绝缘电阻异常");
                }
//                最大速度
                if (vehicleRule.getMaxSpeed() > carMessage.getSpeed()) {
                    log.info("行驶速度正常");
                } else {
                    log.error("行驶速度异常");
                }
//                电机控制器温度
                if (vehicleRule.getMaxTemperature() > carMessage.getMotorControllerTemperature() && vehicleRule.getMinMotorTemperature() < carMessage.getMotorControllerTemperature()) {
                    log.info("电机控制器温度正常");
                } else {
                    log.error("电机控制器温度异常");
                }
//                电机温度
                if (vehicleRule.getMaxMotorTemperature() > carMessage.getMotorTemperature() && vehicleRule.getMinMotorTemperature() < carMessage.getMotorTemperature()) {
                    log.info("电机温度正常");
                } else {
                    log.error("电机温度异常");
                }
//                电机转速
                if (vehicleRule.getRatedSpeed() > carMessage.getMotorSpeed()) {
                    log.info("电机温度正常");
                } else {
                    log.error("电机温度异常");
                }
//                电机转矩
                if (vehicleRule.getMaxTorque() > carMessage.getMotorTorque() && vehicleRule.getMinTorque() < carMessage.getMotorTorque()) {
                    log.info("电机转矩正常");
                } else {
                    log.error("电机转矩异常");
                }
//                电机电压
                if (vehicleRule.getMaxVoltage() > carMessage.getMotorVoltage() && vehicleRule.getMinVoltage() < carMessage.getMotorVoltage()) {
                    log.info("电机电压正常");
                } else {
                    log.error("电机电压异常");
                }
//                电机电流
                if (vehicleRule.getMaxMotorCurrent() > carMessage.getMotorCurrent() && vehicleRule.getMinMotorCurrent() < carMessage.getMotorCurrent()) {
                    log.info("电机电流正常");
                } else {
                    log.error("电机电流异常");
                }
//                动力电池剩余电量SOC
                if (vehicleRule.getMinBatteryCapacity() < carMessage.getPowerBatteryResidue()) {
                    log.info("动力电池剩余电量SOC正常");
                } else {
                    log.error("动力电池剩余电量SOC异常");
                }
//                当前状态允许的最大反馈功率
                if (vehicleRule.getVehicleMaxFeedbackPower() > carMessage.getAtPresentStateAllowMaximumFeedbackPower()) {
                    log.info("当前状态最大反馈功率正常");
                } else {
                    log.error("当前状态最大反馈功率异常");
                }
//                当前状态允许的最大放电功率
                if (vehicleRule.getVehicleMaxDischargePower() > carMessage.getAtPresentStateAllowMaximumDischargingPower()) {
                    log.info("当前状态最大放电功率正常");
                } else {
                    log.error("当前状态最大放电功率异常");
                }
//                燃料消耗率
                if (vehicleRule.getMaxFuelConsumptionRate() > carMessage.getFuelRate()) {
                    log.info("燃耗率正常");
                } else {
                    log.error("燃耗率异常");
                }
//                动力电池充放电电流
                if (vehicleRule.getMinChargeDischargeCurrent() < carMessage.getChargingAndDischargingCurrentPowerBattery() && vehicleRule.getMaxChargeDischargeCurrent() > carMessage.getChargingAndDischargingCurrentPowerBattery()) {
                    log.info("充放电电流正常");
                } else {
                    log.error("充放电电流异常");
                }
//                动力电池负载端总电压V3
                if (vehicleRule.getMaxLoadEndTotalVoltage() > carMessage.getTotalVoltageLoadEndPowerBatteryV3() && vehicleRule.getMinLoadEndTotalVoltage() < carMessage.getTotalVoltageLoadEndPowerBatteryV3()) {
                    log.info("动力电池负载端总电压V3正常");
                } else {
                    log.error("动力电池负载端总电压V3异常");
                }
//              动力电池可用容量
                if (vehicleRule.getMinAvailableCapacity() < carMessage.getPowerBatteryAvailableCapacity()) {
                    log.info("动力电池可用容量正常");
                } else {
                    log.error("动力电池可用容量异常");
                }


//                车辆状态
                if (carMessage.getVehicleState() != 1) {
                    log.error("车辆状态异常");
                }
//                充电状态
                if (carMessage.getChargingState() != 1) {
                    log.error("车辆充电状态异常");
                }
//                运行状态
                if (carMessage.getRunningState() != 1) {
                    log.error("运行状态异常");
                }
//                SOC
                if (carMessage.getSOC() != 1) {
                    log.error("SOC状态异常");
                }
//                可充电储能装置工作状态
                if (carMessage.getWorkingStateRechargeableEnergyStorageDevice() != 1) {
                    log.error("可充电储能装置工作状态异常");
                }
//                驱动电机状态
                if (carMessage.getDriveMotorCondition() != 1) {
                    log.error("驱动电机状态异常");
                }
//                定位是否有效
                if (carMessage.getWhetherState() != 1) {
                    log.error("定位无效异常");
                }
//                EAS
                if (carMessage.getEAS() != 1) {
                    log.error("EAS异常");
                }
//                PTC
                if (carMessage.getPTC() != 1) {
                    log.error("PTC异常");
                }
//                EPS
                if (carMessage.getEPS() != 1) {
                    log.error("EPS异常");
                }
//                ABS
                if (carMessage.getABS() != 1) {
                    log.error("ABS异常");
                }
//                MCU
                if (carMessage.getMCU() != 1) {
                    log.error("MCU异常");
                }
//                动力电池加热状态
                if (carMessage.getPowerBatteryHeatingState() != 1) {
                    log.error("动力电池加热状态异常");
                }
//                动力电池当前状态
                if (carMessage.getPowerBatteryNowState() != 1) {
                    log.error("动力电池当前状态异常");
                }
//                动力电池保温状态
                if (carMessage.getPowerBatteryKeepWarmState() != 1) {
                    log.error("动力电池保温状态异常");
                }
//                DCDC
                if (carMessage.getDCDC() != 1) {
                    log.error("DCDC异常");
                }
//                CHG
                if (carMessage.getCHG() != 1) {
                    log.error("CHG异常");
                }
            }
        }
        return null;
    }


}
