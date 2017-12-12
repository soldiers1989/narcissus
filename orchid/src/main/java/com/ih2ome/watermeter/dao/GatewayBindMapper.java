package com.ih2ome.watermeter.dao;

import com.ih2ome.common.base.MyMapper;
import com.ih2ome.hardware_service.service.model.narcissus.SmartGatewayBind;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayBindMapper extends MyMapper<SmartGatewayBind> {


    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    void deleteGatewayBindByWatermeterId(int watermeterId);
}
