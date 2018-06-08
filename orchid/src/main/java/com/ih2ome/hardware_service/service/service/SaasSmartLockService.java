package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.model.backup.SaasSmartLock;
import com.ih2ome.sunflower.vo.pageVo.Ammeter;
import com.ih2ome.sunflower.vo.pageVo.smartLock.SmartLockDetailVO;
import com.ih2ome.sunflower.vo.pageVo.watermeter.WatermeterDetailVO;

import java.util.List;


public interface SaasSmartLockService {
    /**
     *查询那个房间有云丁门锁
     * @param userId
     * @param type
     * @return
     */
    SaasSmartLock getSmartLock(String userId, String type, String roomId);

    SaasSmartLock getSmartLockCount(String userId,String type);

    SmartLockDetailVO getInformation(String type, String roomId);

    List<WatermeterDetailVO> getWatermeter(String type, String roomId);

    Ammeter getAmmeter(String type, String roomId);
}
