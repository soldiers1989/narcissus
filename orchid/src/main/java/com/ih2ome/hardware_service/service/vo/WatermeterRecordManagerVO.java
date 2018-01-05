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
    private int smartWatermeterId;
    //查询条件开始时间
    private Date startTime;
    //查询条件结束时间
    private Date endTime;

    //抄表时间
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createdAt;
    //设备读数
    private int deviceAmount;
    //当天用水量
    private int dayAmount;
}
