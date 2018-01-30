package com.ih2ome.peony.smartlockInterface;


import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.entity.narcissus.SmartLock;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.GatewayInfoVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;

import java.text.ParseException;
import java.util.List;
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
     * 查询房源设备信息
     *
     * @param userId
     * @param thirdHomeId
     * @return
     * @throws SmartLockException
     */
    public Map<String, Object> searchHouseDeviceInfo(String userId, String thirdHomeId) throws SmartLockException, ParseException;

    /**
     * 查询房间设备信息
     *
     * @param userId
     * @param thirdRoomId
     * @return
     * @throws SmartLockException
     */
    public List<SmartLock> searchRoomDeviceInfo(String userId, String thirdRoomId) throws SmartLockException, ParseException;

}
