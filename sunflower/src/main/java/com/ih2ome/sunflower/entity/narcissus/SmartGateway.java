package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartGateway {

  private long smartGatewayId;
  private java.sql.Timestamp createdAt;
  private long createdBy;
  private java.sql.Timestamp updatedAt;
  private long updatedBy;
  private java.sql.Timestamp deletedAt;
  private long deletedBy;
  private String mac;
  private String sn;
  private String uuid;
  private String model;
  private String modelName;
  private String name;
  private java.sql.Timestamp installTime;
  private String installName;
  private String installMobile;
  private String brand;
  private String operator;
  private double installStatus;
  private double onoffStatus;
  private String remark;
  private long houseCatalog;
  private long apartmentId;
  private long floor;
  private long houseId;
  private long roomId;

}
