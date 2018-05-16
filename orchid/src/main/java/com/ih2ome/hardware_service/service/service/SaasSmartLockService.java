package com.ih2ome.hardware_service.service.service;

import com.ih2ome.sunflower.model.backup.SaasSmartLock;


public interface SaasSmartLockService {
    /**
     *查询那个房间有云丁门锁
     * @param userId
     * @param type
     * @return
     */
    SaasSmartLock getSmartLock(String userId, String type, String roomId);

    SaasSmartLock getSmartLockCount(String userId,String type);
}
