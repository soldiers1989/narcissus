package com.ih2ome.hardware_service.service.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WaterMeterRecordVO {
    private Date createdAt;
    private int deviceAmount;
    private int smartWatermeterId;
}
