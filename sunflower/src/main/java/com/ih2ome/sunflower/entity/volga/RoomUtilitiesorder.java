package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomUtilitiesorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String uuid;
  private long number;
  private double waterFees;
  private double powerFees;
  private double gasFees;
  private double propertyFees;
  private double serviceFees;
  private double upkeepFees;
  private double cleaningFees;
  private double materialFees;
  private double catvFees;
  private double internetFees;
  private double sanitationFees;
  private double otherFees;
  private double freeFees;
  private double totalFees;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private double waterMeter;
  private java.sql.Date waterMeterTime;
  private double powerMeter;
  private java.sql.Date powerMeterTime;
  private long contractId;
  private long evictionOrderId;
  private long orderId;
  private double publicPowerMeter;
  private java.sql.Date publicPowerMeterTime;
  private double gasMeter;
  private java.sql.Date gasMeterTime;
  private long hasReadMeter;

}
