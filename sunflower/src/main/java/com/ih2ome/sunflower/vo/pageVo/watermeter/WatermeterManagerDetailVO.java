package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.Date;

@Data
public class WatermeterManagerDetailVO {
    //水表序列号
    private String uuid;
    //房源名称
    private String homeName;
    //楼层
    private String floorName;
    //房间号
    private String roomName;
    //产品类型1是光电直读湿式冷水表,2是光电直读干式热水表
    private int meterType;
    //绑定时间
    private String createdAt;
    //更新时间
    private String meterUpdatedAt;
    //最近抄表读数
    private int lastAmount;
    //月初水量
    private int meterAmount;
    //通讯状态
    private int onoffStatus;
    //绑定网关
    private String gatewayUuid;

}
