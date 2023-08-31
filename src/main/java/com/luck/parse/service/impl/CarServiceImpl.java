package com.luck.parse.service.impl;

import com.luck.parse.domain.Car;
import com.luck.parse.mapper.CarMapper;
import com.luck.parse.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-25 21:05
 **/
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public Integer selectCarEnterpriseId(String carVin) {
        return null;
    }


    /**
     * 通过vin查找车辆
     * @param vin
     * @return
     */
    @Override
    public Car selectCarByVin(String vin) {
        return carMapper.selectCarByVin(vin);
    }

}
