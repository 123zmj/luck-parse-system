package com.luck.parse.service;

import com.luck.parse.domain.ElectronicFence;
import com.luck.parse.domain.VehicleRules;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p>查询规则</p>
 * @date 2023-08-25 14:36
 **/
public interface FenceService {

    public List<ElectronicFence> selectFence();

}
