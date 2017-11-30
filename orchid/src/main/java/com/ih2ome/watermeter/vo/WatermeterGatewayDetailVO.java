package com.ih2ome.watermeter.vo;

import lombok.Data;

@Data
public class WatermeterGatewayDetailVO {
    //网关编码，房源名称，更新时间，绑定时间，绑定型号，所绑定的水表列表
    private String smartGatewayId;
    private String roomName;
    private String updatedAt;
    private String createdAt;
    private String brand;//绑定型号 （品牌名称）
    //private List watermeterList;//所绑定的水表列表


}
