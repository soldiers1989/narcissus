package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

@Data
public class WatermeterDetailVO {
    //水表序列号，房间号，楼层，房源名称，产品类型-水表型号，绑定时间，当月累计水表量，电费单价，通讯状态，绑定网关，
    private int smartWatermeterId;
    private String roomName;
    private String houseName;
   // private String floorName;
   // private int floorNum;//楼层数
    //private String apartmentName;//房源名称
    private int meterType;//1是光电直读湿式冷水表,2是光电直读干式热水表
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createdAt;
    private int lastAmount;//当月累计水表量 wu当月累计量 = 最近抄表记录 - 月初抄表记录
    private int meterAmount;//月初抄表记录
    private int price;//电费单价
    private int onoffStatus;//通讯状态 wu
    private int smartGatewayId;//绑定网关
    private String sn;//网关序列号


}
