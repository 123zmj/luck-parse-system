package com.luck.parse.fault;

import com.luck.parse.constants.LuckConstants;
import com.luck.parse.domain.FaultInfo;
import com.luck.parse.domain.VehicleFaultLogs;
import com.luck.parse.service.FaultLogsService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 张梦娇
 * @description <p>故障报警mq消费者</p>
 * @date 2023-08-29 09:25
 **/
@Component
@Slf4j
public class FaultMQConsumer {
    @Autowired
    private FaultLogsService faultLogsService;
    @Autowired
    private RedisTemplate redisTemplate;


    //    线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    private static Integer countDay = 0;
    private static Integer countWeek = 0;
    private static Integer countMonth = 0;


    /**
     * 故障管理  报警
     *
     * @param msg
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queuesToDeclare = @Queue(value = LuckConstants.CAR_MESSAGE_FAULT_MQ, durable = "true"))
    public void listen(String msg, Message message, Channel channel) throws IOException {
        try {
            executorService.submit(() -> {
                log.info("消息队列：【{}】，接收到消息：【{}】", LuckConstants.CAR_MESSAGE_FAULT_MQ, msg);
//           从redis获取故障列表信息
                List<FaultInfo> faultInfoList = (List<FaultInfo>) redisTemplate.opsForValue().get(LuckConstants.FAULT_INFO_LIST);
//            故障日志记录集合 一条报文 可能会有多个故障出现 通过集合 一次添加
                List<VehicleFaultLogs> vehicleFaultLogsList = new ArrayList<>();
//            遍历故障码 判断是否存在故障
                for (FaultInfo faultInfo : faultInfoList) {
//                获取故障位
                    Integer faultBit = faultInfo.getFaultBit();
//                截取vin
                    String vin = msg.substring(13, 30);
//                    截取时间戳
                    Long time = Long.valueOf(msg.substring(209));
//                    判断指定的故障位是否有异常 0 为异常 1为正常 将字符串截取 得到某个故障位的字符串 转化为int类型
                    if (0 == Integer.parseInt(msg.substring(faultBit - 1, faultBit))) {
//                        说明车辆发生故障
//                        此时我们要去日志表中去判断有没有次报文对应的故障记录
//                        如果有 且未被解决 也就是没有结束报警时间 我们不做处理
//                        如果有 但是被解决了 或者是 没有此条vin对应的报警 我们在记录表中新增记录
//                        此时我们可以将vin和当前时间存入故障记录集合中 循环结束后 进行批量添加
//                        我们可以将报警存入redis 判断redis中有无此数据 有就不进行操作 没有就新添入redis
//                        如果我们直接去请求数据库 对数据库的压力太大了 我们可以直接用redis

//                        先校验redis里有无此数据 有 不进行记录操作 无进行添加进入redis
                        if (!redisTemplate.hasKey(LuckConstants.FAULT_LOG + vin + faultInfo.getFaultCode())) {
//                            redis中无
                            // 创建故障记录日志对象
                            VehicleFaultLogs vehicleFaultLogs = new VehicleFaultLogs();
                            vehicleFaultLogs.setVin(vin);
                            vehicleFaultLogs.setFaultCode(faultInfo.getFaultCode());
                            vehicleFaultLogs.setStartTime(new Date());
//                        存入redis              用fault_log + vin +故障码做键  防止有重复的
                            redisTemplate.opsForValue().set(LuckConstants.FAULT_LOG + vin +
                                    faultInfo.getFaultCode(), vehicleFaultLogs);
//                        将故障对象 放入集合
                            vehicleFaultLogsList.add(vehicleFaultLogs);
//                            今天产生的故障 进行一个存储  每个车辆发生一次故障 就进行一次累加 数据展示查看的时候可以根据日期进行查看 7天过后不可查看七天前数据
                            redisTemplate.opsForValue().set(LuckConstants.FAULT_DAY_COUNT + getDay(), countDay++, 7, TimeUnit.DAYS);
                        }

                    } else {
//                        说明报文未发生故障
//                        我们可以通过这个vin码去记录表里去查找
//                        有没有这个车辆vin对应的报警记录
//                        如果有 切报警未被解决 则修改此条报警记录的解决时间为当前时间
//                        如果有 但是报警解决了 不做任何处理

//                        从redis里查询有无此数据
//                        有就进行修改数据库里的数据 将当前的时间戳设置为解决故障的时间
//                        解决完之后就将redis里的此条数据进行删除  删除之后 就不会再次查询到已经解决的故障
//                        我们也可以去数据库里进行查询 查询此车对应的故障码 在数据库里对应的此条数据
//                        但是这种方式 对于逻辑要更复杂些
//                        从redis 里获取故障日志记录对象
                        VehicleFaultLogs vehicleFaultLogs = (VehicleFaultLogs) redisTemplate.opsForValue()
                                .get(LuckConstants.FAULT_LOG + vin + faultInfo.getFaultCode());
//                        判断是否为空 为空则说明 没有故障 或者故障已经解决 此时我们针对没有解决的故障进行处理
                        if (vehicleFaultLogs != null) {
//                            说明此故障未解决 但是此时的报文数据是正常无故障的 我们此时可以解决这个故障
//                            并且修改数据库里的故障记录表 将解决时间加上 并从redis中将此对象删除

                        }

                    }


                }
            });
        } catch (Exception e) {
            log.error("签收失败", e);
            /*
             * 记录日志、发送邮件、保存消息到数据库，落库之前判断如果消息已经落库就不保存
             */
            throw new RuntimeException("消息消费失败");
        }
    }


    /**
     * 得到当前的日期 年月日
     */
    public static String getDay() {
//        使用java的日期类型获取当前日期 并格式化为字符串
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return format;
    }

    /**
     * 得到当前的日期 年月日
     */
    public static String getWeek() {
//        使用java的日期类型获取当前日期 并格式化为字符串
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return format;
    }

    /**
     * 得到当前的日期 年月日
     */
    public static String getMonth() {
//        使用java的日期类型获取当前日期 并格式化为字符串
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM").format(date);
        return format;
    }


    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();

        int i = now.getDayOfWeek().getValue() % 7 + 1;

        System.out.println("当前日期："+now);
        System.out.println("当前周的第一天："+dayOfWeek);
        System.out.println("当前周是本年的第："+i+"周");


    }


}
