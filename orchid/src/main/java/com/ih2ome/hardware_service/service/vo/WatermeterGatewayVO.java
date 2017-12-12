package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class WatermeterGatewayVO {
    //网关编码，已绑定水表数，在线数，离线数，在线/离线
    private String smartGatewayId;
    private int bindNum;
    private int onoffNum;
    private int onoffStatus;
}
