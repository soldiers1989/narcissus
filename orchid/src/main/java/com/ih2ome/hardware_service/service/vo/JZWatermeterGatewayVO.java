package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class JZWatermeterGatewayVO {
    //网关编码，已绑定水表总数
    //g.smart_gateway_id,g.onoff_status,COUNT(gb.smart_id) AS watermeter_num,w.onoff_status AS watermter_onoff_status
    private int smartGatewayId;
    private String uuid;//网关编号
    private int onoffStatus;//网关状态，在线离线
    private int watermeterNum;//水表总数
    private int watermeterOnLineNum;//水表在线个数

}
