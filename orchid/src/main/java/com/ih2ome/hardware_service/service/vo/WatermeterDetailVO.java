package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

@Data
public class WatermeterDetailVO {
    private int smartWatermeterId;
    private String uuid;//水表uuid
    private String houseName;//房源名称
    private int meterType;//1是光电直读湿式冷水表,2是光电直读干式热水表
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createdAt;
    private int lastAmount;//当月累计水表量 wu当月累计量 = 最近抄表记录 - 月初抄表记录
    private int meterAmount;//月初抄表记录
    private float price;//电费单价
    private int onoffStatus;//通讯状态 wu
    private int smartGatewayId;//绑定网关
    private String sn;//网关序列号


}
