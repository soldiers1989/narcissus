package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartWatermeter {

  private long smartWatermeterId;
  private java.sql.Timestamp createdAt;
  private long createdBy;
  private java.sql.Timestamp updatedAt;
  private long updatedBy;
  private java.sql.Timestamp deletedAt;
  private long deletedBy;
  private long apartmentId;
  private long floorId;
  private long houseId;
  private long roomId;
  private long houseCatalog;
  private double meterType;
  private String uuid;
  private double onoffStatus;
  private long price;
  private long meterAmount;
  private long lastAmount;
  private java.sql.Timestamp meterUpdatedAt;
  private String manufactory;

}
