package com.luck.parse.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 张梦娇
 * @description <p>故障码实体类</p>
 * @date 2023-08-28 10:16
 **/
@Data
@ToString
public class FaultInfo implements Serializable {
    /**
     * 故障码
     */
    private String faultCode;
    /**
     * 故障类型
     */
    private Integer faultType;
    /**
     * 故障组
     */
    private String groupName;
    /**
     * 故障位
     */
    private Integer faultBit;
    /**
     * 故障值
     */
    private String faultValue;
    /**
     * 故障标签
     */
    private String faultTab;
    /**
     * 是否警告
     */
    private Integer isWarning;
}
