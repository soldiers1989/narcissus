package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomUtilitiesorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long number;
  private double waterFees;
  private double powerFees;
  private double gasFees;
  private double catvFees;
  private double propertyFees;
  private double internetFees;
  private double sanitationFees;
  private double otherFees;
  private double freeFees;
  private double totalFees;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long evictionOrderId;
  private long orderId;
  private long updatedById;
  private double cleaningFees;
  private double materialFees;
  private double serviceFees;
  private double upkeepFees;
  private double powerMeter;
  private java.sql.Date powerMeterTime;
  private double waterMeter;
  private java.sql.Date waterMeterTime;
  private String uuid;
  private double publicPowerMeter;
  private java.sql.Date publicPowerMeterTime;
  private double gasMeter;
  private java.sql.Date gasMeterTime;
  private long hasReadMeter;

}
