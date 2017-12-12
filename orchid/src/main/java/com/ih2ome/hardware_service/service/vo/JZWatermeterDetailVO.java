package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

@Data
public class JZWatermeterDetailVO {
    //w.smart_watermeter_id,r.`name` AS room_name,w.meter_type,w.created_at,w.last_amount,w.meter_amount,w.price,w.onoff_status,g.smart_gateway_id
    //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
    private int smartWatermeterId;
    private String roomName;
    //private String floorName;
    //private String apartmentName;//房源名称
    private int meterType;//1是光电直读湿式冷水表,2是光电直读干式热水表
    private String createdAt;
    private float lastAmount;//当月累计水表量 wu
    private float meterAmount;//月初水量
    private int price;//电费单价
    private int onoffStatus;//通讯状态 wu
    private String smartGatewayId;//绑定网关

}
