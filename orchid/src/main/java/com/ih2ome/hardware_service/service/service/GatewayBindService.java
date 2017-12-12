package com.ih2ome.hardware_service.service.service;

public interface GatewayBindService {
    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    void deleteGatewayBindByWatermeterId(int watermeterId);
}
