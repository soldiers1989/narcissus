package com.ih2ome.watermeter.vo;

import lombok.Data;

@Data
public class WatermeterDetailVO {
    //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
    private int smartWatermeterId;
    private String roomName;
   // private String floorName;
   // private int floorNum;//楼层数
    //private String apartmentName;//房源名称
    private int meterType;//1是光电直读湿式冷水表,2是光电直读干式热水表
    private String createdAt;
    private float lastAmount;//当月累计水表量 wu
    private int price;//电费单价
    private int onoffStatus;//通讯状态 wu
    private String smartGatewayId;//绑定网关

}
