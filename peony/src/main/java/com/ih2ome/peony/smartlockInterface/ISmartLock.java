package com.ih2ome.peony.smartlockInterface;


import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.GuoJiaLockInfoVo;
import com.ih2ome.peony.smartlockInterface.vo.LockPasswordVo;

import java.text.ParseException;

/**
 * 智能门锁第三方接口
 *
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public interface ISmartLock {
    /**
     * 根据门锁编码获取门锁基本信息
     *
     * @param lockNo
     * @return
     */
    public GuoJiaLockInfoVo getGuoJiaLockInfo(String lockNo) throws SmartLockException;

    /**
     * 新增门锁密码
     *
     * @param
     * @return
     * @throws SmartLockException
     */
    public String addLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;
}
