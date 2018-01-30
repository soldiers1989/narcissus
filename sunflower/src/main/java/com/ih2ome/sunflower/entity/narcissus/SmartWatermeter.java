package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.util.Date;

@Data
public class SmartWatermeter {

  private long smartWatermeterId;
  private Date createdAt;
  private long createdBy;
  private Date updatedAt;
  private long updatedBy;
  private Date deletedAt;
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
  private Date meterUpdatedAt;
  private String manufactory;

}
