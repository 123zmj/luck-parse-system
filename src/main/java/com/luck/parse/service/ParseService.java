package com.luck.parse.service;

import com.luck.parse.domain.CarMessage;

import java.util.List;
import java.util.Map;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-28 09:10
 **/
public interface ParseService {
    CarMessage realTimeMessage(String vin);
}
