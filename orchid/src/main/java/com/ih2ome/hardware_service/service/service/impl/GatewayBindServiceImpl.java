package com.ih2ome.hardware_service.service.service.impl;


import com.ih2ome.hardware_service.service.dao.GatewayBindMapper;
import com.ih2ome.hardware_service.service.service.GatewayBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GatewayBindServiceImpl implements GatewayBindService {

    @Resource
    private GatewayBindMapper gatewayBindMapper;

    private static final Logger Log = LoggerFactory.getLogger(GatewayBindServiceImpl.class);

    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    @Override
    public void deleteGatewayBindByWatermeterId(int watermeterId) {
        Log.info("删除网关绑定by水表id，水表watermeterId：{}",watermeterId);
        gatewayBindMapper.deleteGatewayBindByWatermeterId(watermeterId);
    }

    /**
     * 删除网关绑定bygatewayid
     * @param gatewayId
     */
    @Override
    public void deleteGatewayBindByGatewayId(int gatewayId) {
        Log.info("删除网关绑定bygatewayid，gatewayId：{}",gatewayId);
        gatewayBindMapper.deleteGatewayBindByGatewayId(gatewayId);
    }
}
