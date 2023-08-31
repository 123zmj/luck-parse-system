package com.luck.parse.controller;

import com.luck.parse.domain.CarMessage;
import com.luck.parse.service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 张梦娇
 * @description <p>解析系统控制器</p>
 * @date 2023-08-28 09:07
 **/
@RestController
@RequestMapping("/parse")
@Slf4j
public class ParseController {
    @Autowired
    private ParseService parseService;


    @GetMapping("/real/time/message/{vin}")
    public CarMessage realTimeMessage(@PathVariable("vin") String vin){
       return parseService.realTimeMessage(vin);
    }




}
