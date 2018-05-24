package com.ih2ome.sunflower.vo.pageVo.watermeter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Data;

import java.util.Date;

/**
 * @author Hunter Pan
 * create by 2018/5/23
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class WaterSimpleVO {

    private int waterId;

    // 1冷 2热
    private int meterType;

    private String updateAt;

    //当月读数 当月累计量 = 最近抄表记录 - 月初抄表记录
    private int currentAmount;

    //通讯状态 1 Online 2 Offline
    private int onoffStatus;
}
