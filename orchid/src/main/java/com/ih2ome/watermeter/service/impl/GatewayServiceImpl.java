package com.ih2ome.watermeter.service.impl;

import com.ih2ome.watermeter.dao.GatewayMapper;
import com.ih2ome.watermeter.service.GatewayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GatewayServiceImpl implements GatewayService{
    @Resource
    private GatewayMapper gatewayMapper;
    /**
     * 查找网关id
     * @param gateway_uuid
     * @return
     */
    @Override
    public int findGatewayIdByUuid(String gateway_uuid) {
        return gatewayMapper.selectGatewayIdByUuid(gateway_uuid);
    }
}
