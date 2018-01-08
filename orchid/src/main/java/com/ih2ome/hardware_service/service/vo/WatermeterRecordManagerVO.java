package com.ih2ome.hardware_service.service.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WatermeterRecordManagerVO extends BaseEntity implements Serializable {
    //水表设备表ID
    private String smartWatermeterId;
    //查询条件开始时间
    private String startTime;
    //查询条件结束时间
    private String endTime;

    //抄表时间
    private String createdAt;
    //设备读数
    private int deviceAmount;
    //当天用水量
    private int dayAmount;
}
