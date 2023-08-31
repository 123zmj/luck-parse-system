package com.luck.parse.service;

import com.luck.parse.domain.Car;

/**
 * @author 张梦娇
 * @description <p>车辆</p>
 * @date 2023-08-25 21:04
 **/
public interface CarService {

    public Integer selectCarEnterpriseId(String carVin);

    /**
     * 通过vin查找车辆
     * @param vin
     * @return
     */
    Car selectCarByVin(String vin);
}
