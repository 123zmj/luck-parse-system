package com.luck.parse.service.impl;

import com.luck.parse.consumer.ConsumerMessage;
import com.luck.parse.domain.CarMessage;
import com.luck.parse.service.ParseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-28 09:11
 **/
@Service
public class ParseServiceImpl implements ParseService {
    @Override
    public  CarMessage realTimeMessage(String vin) {
        return ConsumerMessage.messageMap.get(vin);
    }
}
