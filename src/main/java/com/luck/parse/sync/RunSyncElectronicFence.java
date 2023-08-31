package com.luck.parse.sync;

import com.luck.parse.domain.ElectronicFence;
import com.luck.parse.service.FenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张梦娇
 * @description <p>将电子围栏缓存到本地内存</p>
 * @date 2023-08-25 20:54
 **/
@Component
public class RunSyncElectronicFence implements ApplicationRunner {
    //    我们将全部的电子围栏信息全部放到内存中
    public static Map<String, List<ElectronicFence>> fencesMap = new HashMap<>();

    @Autowired
    private static FenceService fenceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


    }


    /**
     * 查询正在使用中的电子围栏
     * 调用围栏service里的方法查询所有的正在使用的电子围栏
     *
     * @return
     */
    public static List<ElectronicFence> selectUsingFence() {
        List<ElectronicFence> electronicFences = fenceService.selectFence();
        return electronicFences;
    }
}
