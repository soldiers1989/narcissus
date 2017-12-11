package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartWatermeter implements Serializable{

  private Long smartWatermeterId;
  private java.sql.Timestamp createdAt;
  private Long createdBy;
  private java.sql.Timestamp updatedAt;
  private Long updatedBy;
  private java.sql.Timestamp deletedAt;
  private Long deletedBy;
  private Long apartmentId;
  private Long floorId;
  private Long houseId;
  private Long roomId;
  private Long houseCatalog;
  //TODO:double float
  private Double meterType;
  private String uuid;
  private Double onoffStatus;
  private Long price;
  private Long meterAmount;
  private Long lastAmount;
  private java.sql.Timestamp meterUpdatedAt;
  private String manufactory;

}
