package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GatewayWebDetailVO implements Serializable{
    //id,网关编码，房源名称，更新时间，绑定时间，绑定型号，所绑定的水表列表
    private int smartGatewayId;
    private String uuid;
    private String installAdress;
    private String onoffStatus;
    private String updatedAt;
    private String createdAt;
    private String brand;//厂商 （品牌名称）
    private int type;//分散式集中式

    private List<GatewayWatermeterWebListVO> watermeterList;//所绑定的水表列表


}
