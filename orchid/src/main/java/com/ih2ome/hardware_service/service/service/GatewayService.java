package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;

public interface GatewayService {
    /**
     * 查询网关idByuuid
     * @param gateway_uuid
     * @return
     */
    int findGatewayIdByUuid(String gateway_uuid);

    /**
     * 添加网关
     * @param smartGateway
     */
    void addSmartGateway(SmartGateway smartGateway);

    /**
     * 替换网关
     * @param uuid
     * @param old_uuid
     * @param time
     * @param manufactory
     */
    void updataGatewayUuid(String uuid, String old_uuid, int time, String manufactory);

    /**
     * 添网关异常
     * @param smartMistakeInfo
     */
    void addSmartMistakeInfo(SmartMistakeInfo smartMistakeInfo);
}
