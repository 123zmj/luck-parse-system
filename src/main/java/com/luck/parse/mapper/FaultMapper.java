package com.luck.parse.mapper;

import com.luck.parse.domain.FaultInfo;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-29 19:04
 **/
public interface FaultMapper {

    List<FaultInfo> selectFaultInfoList(FaultInfo faultInfo);

}
