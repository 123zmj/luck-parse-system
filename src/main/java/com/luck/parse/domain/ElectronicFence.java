package com.luck.parse.domain;

import lombok.Data;

/**
 * @author 张梦娇
 * @description <p>电子围栏实体类</p>
 * @date 2023-08-25 20:55
 **/
@Data
public class ElectronicFence {
    /**
     *自增ID
     */
    private Integer id;
    /**
     *围栏名称
     */
    private String fenceName;
    /**
     *经纬度数组
     */
    private String longitudeLatitude;
    /**
     *围栏状态（1正常  2.错误）
     */
    private Integer fenceStatus;
    /**
     *企业ID
     */
    private Integer enterpriseId;
    /**
     *电子围栏类型外键id（1.驶入 2.驶出）
     */
    private Integer fenceTypeid;



}
