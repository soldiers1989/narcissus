package com.ih2ome.hardware_service.service.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayMapper extends MyMapper<SmartGateway> {
    /**
     * 查找网关idByuuid
     * @param gateway_uuid
     * @return
     */
    Integer selectGatewayIdByUuid(String gateway_uuid);

    /**
     * 添加网关
     * @param smartGateway
     */
    void insertSmartGateway(SmartGateway smartGateway);

    /**
     * 更新网关
     * @param uuid
     * @param old_uuid
     * @param time
     * @param manufactory
     */
    void updateGatewayUuid(@Param("uuid") String uuid, @Param("old_uuid") String old_uuid, @Param("time") int time, @Param("manufactory") String manufactory);

    /**
     * 添加网关异常
     * @param smartMistakeInfo
     */
    void addSmartMistakeInfo(SmartMistakeInfo smartMistakeInfo);

    /**
     * 更新网关在线离线状态byuuid
     * @param uuid
     * @param code
     */
    void updataGatewayOnoffStatusByUuid(@Param("uuid") String uuid, @Param("code") Integer code);
}
