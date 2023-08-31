package com.luck.parse.service.impl;

import com.luck.parse.domain.FaultInfo;
import com.luck.parse.mapper.FaultMapper;
import com.luck.parse.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p>故障码管理</p>
 * @date 2023-08-28 10:16
 **/
@Service
public class FaultServiceImpl implements FaultService {

    @Autowired
    private FaultMapper faultMapper;


    @Override
    public List<FaultInfo> selectFaultInfoList(FaultInfo faultInfo) {
        return faultMapper.selectFaultInfoList(faultInfo);
    }
}
