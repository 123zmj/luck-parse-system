package com.luck.parse.mapper;


import com.luck.parse.domain.ElectronicFence;

import java.util.List;

/**
 * @author 张梦娇
 * @description <p>电子围栏</p>
 * @date 2023-08-25 14:46
 **/
public interface FenceMapper {


    List<ElectronicFence> listInUse();

}
