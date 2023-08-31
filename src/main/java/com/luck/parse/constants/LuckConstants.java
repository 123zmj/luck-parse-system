package com.luck.parse.constants;

import lombok.Data;

/**
 * @author 张梦娇
 * @description <p>本地内存 存储名称</p>
 * @date 2023-08-25 23:44
 **/
@Data
public class LuckConstants {
    /**
     * 本地内存存储规则
     */
    public static final String RULE_MAP = "rules_map";
    /**
     * 本地内存存储电子围栏
     */
    public static final String FENCE_MAP = "fence_map";
    /**
     * redis存储车辆报文
     */
    public static final String MESSAGE_CAR = "message_car";
    /**
     * mq 队列
     */
    public static final String CAR_MESSAGE_MQ ="car_message_mq";
    /**
     * mq 故障校验队列
     */
    public static final String CAR_MESSAGE_FAULT_MQ ="car_message_fault_mq";
    /**
     * mq 落库hbase队列
     */
    public static final String CAR_MESSAGE_HBASE_MQ ="car_message_hbase_mq";
    /**
     * mq 实时数据队列
     */
    public static final String CAR_MESSAGE_REAL_TIME_MQ ="car_message_real_time_mq";
    /**
     * mq 电子围栏校验队列
     */
    public static final String CAR_MESSAGE_FENCE_MQ ="car_message_fence_mq";
    /**
     * 故障码列表存储
     */
    public static final String FAULT_INFO_LIST="fault_list";
    /**
     * 故障日志暂存
     */
    public static final String FAULT_LOG ="fault_log";

    /**
     * 日故障量
     */
    public static final String FAULT_DAY_COUNT ="fault_day_count:";
    /**
     * 周故障量
     */
    public static final String FAULT_WEEK_COUNT ="fault_day_count:";
    /**
     * 月故障量
     */
    public static final String FAULT_MONTH_COUNT ="fault_day_count:";


}
