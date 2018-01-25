package com.ih2ome.peony.smartlockInterface;


import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;

import java.text.ParseException;
import java.util.Map;

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
    public LockVO getLockInfo(String lockNo) throws SmartLockException;

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
    public String deleteLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;

    /**
     * 冻结门锁密码
     *
     * @param lockPassword
     * @return
     */
    public String frozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;

    /**
     * 解冻门锁密码
     *
     * @param lockPassword
     * @return
     */
    public String unfrozenLockPassword(LockPasswordVo lockPassword) throws SmartLockException, ParseException;

    /**
     * 查询用户下所有的房源和设备信息
     *
     * @return
     * @throws SmartLockException
     */
    public String searchHomeInfo(Map<String, Object> params) throws SmartLockException;

    /**
     * 根据房间id查询网关信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    public String searchGataWayInfo(Map<String, Object> params) throws SmartLockException;

    /**
     * 根据房间id查询门锁信息
     *
     * @param params
     * @return
     * @throws SmartLockException
     */
    public String searchLockInfo(Map<String, Object> params) throws SmartLockException;
}
