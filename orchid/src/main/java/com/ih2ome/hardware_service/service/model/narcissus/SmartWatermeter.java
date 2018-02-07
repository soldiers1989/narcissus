package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SmartWatermeter implements Serializable{

  private int smartWatermeterId;
  private Date createdAt;
  private Long createdBy;
  private Date updatedAt;
  private Long updatedBy;
  private Date deletedAt;
  private Long deletedBy;
  private Long apartmentId;
  private Integer floorId;
  private Long houseId;
  private Long roomId;
  private int houseCatalog;
  private int meterType;
  private String uuid;
  private int onoffStatus;
  private Long price;
  private Long meterAmount;
  private Long lastAmount;
  private Date meterUpdatedAt;
  private String manufactory;

}
