package com.luck.parse.sync;

import com.luck.parse.constants.LuckConstants;
import com.luck.parse.domain.VehicleRules;
import com.luck.parse.service.RulesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 张梦娇
 * @description <p>启动时同步车辆绑定规则</p>
 * @date 2023-08-25 14:12
 **/
@Component
@Slf4j
public class RunSyncRules implements ApplicationRunner {

    @Autowired
    private  RulesService rulesService;
    /**
     * 创建一个静态map 存放数据库中查询到的规则
     */
    public static Map<String, List<VehicleRules>> rulesMap = new HashMap<String, List<VehicleRules>>();

//
//    /**
//     * 查询所有的规则
//     *
//     * @return
//     */
//    public static List<VehicleRules> selectRules() {
//
//        List<VehicleRules> vehicleRules = rulesService.selectRules();
//        return vehicleRules;
//    }

    /**
     * 同步到内存
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<VehicleRules> vehicleRules = rulesService.selectRules();
                log.info("将车辆规则存入到内存");

                try {
                    rulesMap.put(LuckConstants.RULE_MAP, vehicleRules);
                    log.info("存储完成啦");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 30, TimeUnit.SECONDS);
    }


}
