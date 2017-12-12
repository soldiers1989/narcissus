package com.ih2ome.watermeter.service;

public interface GatewayService {
    /**
     * 查询网关idByuuid
     * @param gateway_uuid
     * @return
     */
    int findGatewayIdByUuid(String gateway_uuid);
}
