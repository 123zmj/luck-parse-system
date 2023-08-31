package com.luck.parse.mapper;

import com.luck.parse.domain.Car;
import org.apache.ibatis.annotations.Param;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-25 21:05
 **/
public interface CarMapper {
    Car selectCarByVin(@Param("vin") String vin);
}
