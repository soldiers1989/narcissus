package com.ih2ome.peony.smartlockInterface;


import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.peony.smartlockInterface.vo.GatewayInfoVO;
import com.ih2ome.peony.smartlockInterface.vo.LockInfoVO;
import com.ih2ome.peony.smartlockInterface.vo.guojia.LockPasswordVo;

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
    public LockInfoVO getLockInfo(String lockNo) throws SmartLockException;

    /**
     * 根据网关编码获取网关基本信息
     *
     * @param gateNo
     * @return
     * @throws SmartLockException
     */
    public GatewayInfoVO getGateWayInfo(String gateNo) throws SmartLockException;

    /**
     * 新增门锁密码
     *
     * @param
     * @return
     * @throws SmartLockException
     */
    public String addLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;

    /**
     * 修改门锁密码
     *
     * @param lockPassword
     * @return
     */
    public String updateLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;

    /**
     * 删除门锁密码
     *
     * @param lockPassword
     * @return
     */
    public String deleteLockPassword(LockPasswordVo lockPassword) throws SmartLockException;
}
