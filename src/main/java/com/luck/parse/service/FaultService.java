package com.luck.parse.service;

import com.luck.parse.domain.FaultInfo;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p>故障码管理</p>
 * @date 2023-08-28 10:15
 **/
public interface FaultService {
    /**
     * 查询车辆故障管理列表
     *
     * @param faultInfo 车辆故障管理
     * @return 车辆故障管理集合
     */
    public List<FaultInfo> selectFaultInfoList(FaultInfo faultInfo);
}
