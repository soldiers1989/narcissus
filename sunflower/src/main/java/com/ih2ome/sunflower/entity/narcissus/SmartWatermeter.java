package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.util.Date;

@Data
public class SmartWatermeter {

  private Integer smartWatermeterId;
  private Date createdAt;
  private long createdBy;
  private Date updatedAt;
  private long updatedBy;
  //private Date deletedAt;
  private long deletedBy;
  private Integer apartmentId;
  private Integer floorId;
  private Integer houseId;
  private Integer roomId;
  private Integer houseCatalog;
  private Integer meterType;
  private String uuid;
  private Integer onoffStatus;
  private int price;
  private Long meterAmount;
  private Integer lastAmount;
  private Date meterUpdatedAt;
  private String manufactory;
  private  String meter;

}
