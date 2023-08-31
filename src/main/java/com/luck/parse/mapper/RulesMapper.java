package com.luck.parse.mapper;

import com.luck.parse.domain.VehicleRules;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p>规则</p>
 * @date 2023-08-25 14:46
 **/
public interface RulesMapper {
    List<VehicleRules> list();
}
