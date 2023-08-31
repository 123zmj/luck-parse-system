package com.luck.parse.fault;

import com.alibaba.fastjson.JSONObject;
import com.luck.parse.constants.LuckConstants;
import com.luck.parse.domain.FaultInfo;
import com.luck.parse.service.FaultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author 张梦娇
 * @description <p>项目启动时将车辆故障码同步redis</p>
 * @date 2023-08-29 18:58
 **/
@Component
@Slf4j
public class SyncFaultListRunner implements ApplicationRunner {


    @Autowired
    private FaultService faultService;


    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        获取所有的故障码存入redis
        log.info("将故障码存入redis——————————————————————————");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        List<FaultInfo> faultInfos = faultService.selectFaultInfoList(null);
        redisTemplate.opsForValue().set(LuckConstants.FAULT_INFO_LIST, faultInfos);


        log.info(redisTemplate.opsForValue().get(LuckConstants.FAULT_INFO_LIST)+"");

    }
}
