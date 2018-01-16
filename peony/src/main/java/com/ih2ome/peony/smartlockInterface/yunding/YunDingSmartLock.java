package com.ih2ome.peony.smartlockInterface.yunding;

import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.GatewayInfoVO;
import com.ih2ome.peony.smartlockInterface.vo.LockInfoVO;
import com.ih2ome.peony.smartlockInterface.vo.guojia.LockPasswordVo;

import java.text.ParseException;

/**
 * @author Sky
 * @create 2018/01/11
 * @email sky.li@ixiaoshuidi.com
 **/
public class YunDingSmartLock implements ISmartLock{

    @Override
    public LockInfoVO getLockInfo(String lockNo) throws SmartLockException {
        return null;
    }

    @Override
    public GatewayInfoVO getGateWayInfo(String gateNo) throws SmartLockException {
        return null;
    }

    @Override
    public String addLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        return null;
    }

    @Override
    public String updateLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException {
        return null;
    }

    @Override
    public String deleteLockPassword(LockPasswordVo lockPassword) throws SmartLockException {
        return null;
    }
}
