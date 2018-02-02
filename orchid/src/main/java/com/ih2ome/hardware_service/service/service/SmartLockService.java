package com.ih2ome.hardware_service.service.service;

import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.entity.narcissus.SmartLockPassword;
import com.ih2ome.sunflower.model.backup.HomeVO;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartHouseMappingVO;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.LockPasswordVo;

import java.text.ParseException;
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
}
