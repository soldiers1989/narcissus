package com.ih2ome.hardware_service.service.service.impl;

import com.ih2ome.hardware_service.service.dao.GatewayMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGateway;
import com.ih2ome.hardware_service.service.model.narcissus.SmartMistakeInfo;
import com.ih2ome.hardware_service.service.service.GatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GatewayServiceImpl implements GatewayService {
    @Resource
    private GatewayMapper gatewayMapper;

    private static final Logger Log = LoggerFactory.getLogger(GatewayServiceImpl.class);
    /**
     * 查找网关id
     * @param gateway_uuid
     * @return
     */
    @Override
    public int findGatewayIdByUuid(String gateway_uuid) {
        Log.info("查找网关id，网关gateway_uuid：{}",gateway_uuid);
        Integer res = gatewayMapper.selectGatewayIdByUuid(gateway_uuid);
        //没查到返回负数
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
        Log.info("添加网关，smartGateway：{}",smartGateway.toString());
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
        Log.info("替换网关，新网关uuid：{},老网关old_uuid:{},时间time：{},厂商manufactory:{}",uuid,old_uuid,time,manufactory);
        gatewayMapper.updateGatewayUuid(uuid,old_uuid,time,manufactory);
    }

    /**
     * 添加异常信息
     * @param smartMistakeInfo
     */
    @Override
    public void addSmartMistakeInfo(SmartMistakeInfo smartMistakeInfo) {
        Log.info("添加异常信息，smartMistakeInfo：{}",smartMistakeInfo.toString());
        gatewayMapper.addSmartMistakeInfo(smartMistakeInfo);
    }

    /**
     * 更新网关在线离线状态
     * @param uuid
     * @param code
     */
    @Override
    public void updataGatewayOnoffStatus(String uuid, Integer code) {
        Log.info("更新网关在线离线状态，网关uuid：{},状态码code：",uuid,code);
        gatewayMapper.updataGatewayOnoffStatusByUuid(uuid,code);
    }


}
