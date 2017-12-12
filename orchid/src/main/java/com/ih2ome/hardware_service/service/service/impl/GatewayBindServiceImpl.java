package com.ih2ome.hardware_service.service.service.impl;


import com.ih2ome.hardware_service.service.dao.GatewayBindMapper;
import com.ih2ome.hardware_service.service.service.GatewayBindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GatewayBindServiceImpl implements GatewayBindService {

    @Resource
    private GatewayBindMapper gatewayBindMapper;

    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    @Override
    public void deleteGatewayBindByWatermeterId(int watermeterId) {
        gatewayBindMapper.deleteGatewayBindByWatermeterId(watermeterId);
    }
}
