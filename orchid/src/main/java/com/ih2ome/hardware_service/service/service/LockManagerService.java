package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.LockManagerVo;

import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
public interface LockManagerService {

    //门锁列表
    List<LockManagerVo> lockList(LockManagerVo lockManagerVo);
}
