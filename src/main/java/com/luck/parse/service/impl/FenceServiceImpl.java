package com.luck.parse.service.impl;

import com.luck.parse.domain.ElectronicFence;
import com.luck.parse.mapper.FenceMapper;
import com.luck.parse.service.FenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-25 20:59
 **/
@Service
public class FenceServiceImpl implements FenceService {

    @Autowired
    private FenceMapper fenceMapper;
    @Override
    public List<ElectronicFence> selectFence() {
        return fenceMapper.listInUse();
    }
}
