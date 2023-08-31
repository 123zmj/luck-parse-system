package com.luck.parse.mqconsumer;

import com.luck.parse.constants.LuckConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 张梦娇
 * @description <p>电子围栏mq消费者</p>
 * @date 2023-08-29 09:27
 **/
@Component
@Slf4j
public class FenceMQConsumer {


    @RabbitListener(queuesToDeclare = @Queue(value = LuckConstants.CAR_MESSAGE_MQ, durable = "true"))
    public void listen(String object, Message message, Channel channel) throws IOException {


        try {
            /*
             *
             * 执行业务代码...
             *

             */
//            int i = 1 / 0; //故意报错测试
        } catch (Exception e) {
            log.error("签收失败", e);
            /*
             * 记录日志、发送邮件、保存消息到数据库，落库之前判断如果消息已经落库就不保存
             */
            throw new RuntimeException("消息消费失败");
        }
    }
}
