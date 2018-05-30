package com.ih2ome.hardware_service.service.service;

import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.entity.narcissus.SmartMistakeInfo;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.LockInfoVo;
import com.ih2ome.sunflower.vo.pageVo.smartLock.PasswordRoomVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;
import com.ih2ome.sunflower.vo.thirdVo.yunDingCallBack.CallbackRequestVo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
public interface SmartLockService {
    /**
     * 查询第三方房屋和水滴房屋信息
     *
     * @param type
     * @return
     */
    Map<String, List<HomeVO>> searchHome(String userId, String type, String factoryType) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SmartLockException;

    /**
     * 取消房间关联
     */
    void cancelAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException;

    /**
     * 关联房间
     *
     * @param smartHouseMappingVO
     */
    void confirmAssociation(SmartHouseMappingVO smartHouseMappingVO) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException, ParseException;

    /**
     * 根据门锁id查询门锁密码列表
     *
     * @param lockId
     * @return
     */
    List<SmartLockPassword> findPasswordList(String lockId) throws SmartLockException;

    /**
     * 新增门锁密码
     *
     * @param
     */
    void addLockPassword(LockPasswordVo passwordVo) throws SmartLockException, IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException;

    /**
     * 删除门锁密码
     *
     * @param password_id
     */
    void deleteLockPassword(String password_id, String userId) throws SmartLockException, IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException;

    /**
     * 修改门锁密码
     *
     * @param passwordVo
     */
    void updateLockPassword(LockPasswordVo passwordVo) throws SmartLockException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException;

    /**
     * 冻结门锁密码
     *
     * @param userId
     * @param password_id
     */
    void frozenLockPassword(String userId, String password_id) throws ClassNotFoundException, SmartLockException, InstantiationException, IllegalAccessException, ParseException;

    /**
     * 解冻门锁密码
     *
     * @param userId
     * @param passwordId
     */
    void unFrozenLockPassword(String userId, String passwordId) throws ClassNotFoundException, SmartLockException, InstantiationException, IllegalAccessException, ParseException;

    /**
     * 更新门锁自动催收功能
     * @param passwordId
     * @param autoCollection
     * @return
     */
    int updateAutoCollection(int passwordId, int autoCollection);

    /**
     * 根据门锁Id查询门锁详情
     *
     * @param lockId
     * @return
     */
    SmartLockDetailVO findSmartLockDetail(String lockId) throws SmartLockException;

    /**
     * 本地添加密码
     *
     * @param passwordVo
     */
    void addLockPasswordCallBack(LockPasswordVo passwordVo);

    /**
     * 本地更新密码
     *
     * @param passwordVo
     */
    void updateLockPasswordCallBack(LockPasswordVo passwordVo);

    /**
     * 本地删除密码
     *
     * @param uuid
     */
    void deleteLockPasswordCallBack(String uuid);

    /**
     * 查询开门记录
     *
     * @param lockId
     * @return
     */
    Map<String, ArrayList<SmartMistakeInfo>> findOpenLockRecord(String lockId) throws SmartLockException;

    /**
     * 查询操作记录
     *
     * @param lockId
     * @return
     */
    Map<String, ArrayList<SmartMistakeInfo>> findHistoryOperations(String lockId) throws SmartLockException;
    /**
     * 查询异常记录
     *
     * @param lockId
     * @return
     */
    Map<String, ArrayList<SmartMistakeInfo>> findExceptionRecords(String lockId) throws SmartLockException;

    /**
     * 更新门锁剩余电量
     *
     * @param lockInfoVo
     */
    void updateBatteryInfo(LockInfoVo lockInfoVo);

    /**
     * 解绑门锁
     *
     * @param uuid
     */
    void uninstallSmartLock(String uuid);

    /**
     * 绑定门锁
     *
     * @param homeId
     * @param roomId
     * @param uuid
     */
    void installSmartLock(String homeId, String roomId, String uuid);

    /**
     * 获取密码+房间信息
     * @return
     */
    List<PasswordRoomVO> getPasswordRoomList();

    boolean judgeRoomOverdue(PasswordRoomVO passwordRoom, String smsBaseUrl);
    void sendFrozenMessage(PasswordRoomVO passwordRoom, String smsBaseUrl);
}
