package com.ih2ome.sunflower.vo.pageVo.watermeter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

/**
 * @author Hunter Pan
 * create by 2018/5/24
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class WaterDetailVO {
    private int waterId;
    private String waterUuid;//水表uuid
    private int meterType;//1是光电直读湿式冷水表,2是光电直读干式热水表
    private String updateAt;//读数更新时间
    private int currentAmount;//当前读书 = 最近抄表记录 - 月初抄表记录
    private float price;//电费单价
    private int onoffStatus;//通讯状态 wu
    private int smartGatewayId;//绑定网关
    private String gatewayUuId;//网关序列号
}
