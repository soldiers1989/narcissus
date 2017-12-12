package com.ih2ome.watermeter.service.impl;

import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
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
        Integer res = gatewayMapper.selectGatewayIdByUuid(gateway_uuid);
        if (res ==null){
            return -1;
        }
        return res;
    }

    /**
     * 添加网关
     * @param smartGateway
     */
    @Override
    public void addSmartGateway(SmartGateway smartGateway) {
        gatewayMapper.insertSmartGateway(smartGateway);
    }

    /**
     * 替换网关
     * @param uuid
     * @param old_uuid
     * @param time
     * @param manufactory
     */
    @Override
    public void updataGatewayUuid(String uuid, String old_uuid, int time, String manufactory) {
        gatewayMapper.updateGatewayUuid(uuid,old_uuid,time,manufactory);
    }

    @Override
    public void addSmartMistakeInfo(SmartMistakeInfo smartMistakeInfo) {
        gatewayMapper.addSmartMistakeInfo(smartMistakeInfo);
    }


}
