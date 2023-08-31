package com.luck.parse.service.impl;

import com.luck.parse.mapper.FaultLogsMapper;
import com.luck.parse.service.FaultLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 张梦娇
 * @description <p></p>
 * @date 2023-08-29 20:00
 **/
@Service
public class FaultLogsServiceImpl implements FaultLogsService {
    @Autowired
    private FaultLogsMapper faultLogsMapper;

}
