package com.ih2ome.hardware_service.service.service;

import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;

public interface GatewayService {
    /**
     * 查询网关idByuuid
     * @param gateway_uuid
     * @return
     */
    Integer findGatewayIdByUuid(String gateway_uuid);

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
    void updataGatewayUuid(String uuid, String old_uuid, Long time, String manufactory);

    /**
     * 添网关异常
     * @param smartMistakeInfo
     */
    void addSmartMistakeInfo(SmartMistakeInfo smartMistakeInfo);

    /**
     * 更改网关在线离线状态
     * @param uuid
     * @param code
     */
    void updataGatewayOnoffStatus(String uuid, Integer code);
}
