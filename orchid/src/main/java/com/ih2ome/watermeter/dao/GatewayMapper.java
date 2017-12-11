package com.ih2ome.watermeter.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayMapper extends MyMapper<SmartGateway> {
    /**
     * 查找网关idByuuid
     * @param gateway_uuid
     * @return
     */
    int selectGatewayIdByUuid(String gateway_uuid);
}
