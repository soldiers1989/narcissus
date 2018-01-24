package com.ih2ome.hardware_service.service.service;

import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.vo.pageVo.smartLock.HomeVO;

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
     *
     * @param type
     * @param roomId
     * @param thirdRoomId
     */
    void cancelAssociation(String type, String roomId, String thirdRoomId);
}
