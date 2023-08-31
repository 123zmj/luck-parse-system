package com.luck.parse.config;

import com.luck.parse.constants.LuckConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitmqConfig
 * @Description TODO
 * @Author 张梦娇
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 创建消息故障处理队列
     *
     * @return
     */
    @Bean(LuckConstants.CAR_MESSAGE_FAULT_MQ)
    public Queue CAR_MESSAGE_FAULT_MQ() {
        return new Queue(LuckConstants.CAR_MESSAGE_FAULT_MQ, true);  //设置消息持久化 防丢失
    }


    /**
     * 创建落库hbase队列
     *
     * @return
     */
    @Bean(LuckConstants.CAR_MESSAGE_HBASE_MQ)
    public Queue CAR_MESSAGE_HBASE_MQ() {
        return new Queue(LuckConstants.CAR_MESSAGE_HBASE_MQ, true); //设置消息持久化 防丢失
    }


    /**
     * 创建实时数据反馈队列
     *
     * @return
     */
    @Bean(LuckConstants.CAR_MESSAGE_REAL_TIME_MQ)
    public Queue CAR_MESSAGE_REAL_TIME_MQ() {
        return new Queue(LuckConstants.CAR_MESSAGE_REAL_TIME_MQ, true); //设置消息持久化 防丢失
    }


    /**
     * 创建电子围栏校验
     *
     * @return
     */
    @Bean(LuckConstants.CAR_MESSAGE_FENCE_MQ)
    public Queue CAR_MESSAGE_FENCE_MQ() {
        return new Queue(LuckConstants.CAR_MESSAGE_FENCE_MQ, true); //设置消息持久化 防丢失
    }


}
