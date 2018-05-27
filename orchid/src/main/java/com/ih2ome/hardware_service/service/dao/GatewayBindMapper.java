package com.ih2ome.hardware_service.service.dao;


import com.ih2ome.common.base.MyMapper;
import com.ih2ome.sunflower.entity.narcissus.SmartGatewayBind;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatewayBindMapper extends MyMapper<SmartGatewayBind> {


    /**
     * 删除网关绑定by水表id
     * @param watermeterId
     */
    void deleteGatewayBindByWatermeterId(int watermeterId);

    /**
     * 删除网关绑定by网关id
     * @param gatewayId
     */
    void deleteGatewayBindByGatewayId(int gatewayId);

    /**
     * 网关下所有水表id
     * @param gatewayId
     * @return
     */
    List<String> findeGatewayWatermeterId(String gatewayId);

    /**
     * 修改水表删除状态
     * @param id
     */
    void  updateWatermeterStatus(String id);


}
