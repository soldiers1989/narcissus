package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.vo.LockInfoVo;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;
import com.ih2ome.hardware_service.service.vo.LockRequestVo;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;

import java.text.ParseException;
import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
public interface LockManagerService {

    //门锁列表
    List<LockListVo> lockList(LockListVo lockListVo);

    //根据门锁编码查询门锁基本信息
    LockInfoVo getLockInfoVo(String lockNo, String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException;

    //根据门锁编码查询门锁密码列表
    List<LockPasswordVo> getPwdList(LockRequestVo lockRequestVo);

    //新增门锁密码
    void addPassword(LockPasswordVo lockPasswordVo) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException, ParseException;
}
