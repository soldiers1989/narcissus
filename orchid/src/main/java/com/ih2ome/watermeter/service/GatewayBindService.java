package com.ih2ome.watermeter.service;

public interface GatewayBindService {
    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    void deleteGatewayBindByWatermeterId(int watermeterId);
}
