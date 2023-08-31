package com.luck.parse.service.impl;

import com.luck.parse.domain.VehicleRules;
import com.luck.parse.mapper.RulesMapper;
import com.luck.parse.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-25 14:45
 **/
@Service
public class RulesServiceImpl implements RulesService {
    @Autowired
    private RulesMapper rulesMapper;

    /**
     * 查询所有在使用的电子围栏
     * @return
     */
    @Override
    public List<VehicleRules> selectRules() {
        return rulesMapper.list();
    }


}
