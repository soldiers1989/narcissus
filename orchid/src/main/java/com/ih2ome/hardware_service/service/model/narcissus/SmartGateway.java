package com.ih2ome.hardware_service.service.model.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartGateway implements Serializable{

  private Long smartGatewayId;
  private java.sql.Timestamp createdAt;
  private Long createdBy;
  private java.sql.Timestamp updatedAt;
  private Long updatedBy;
  private java.sql.Timestamp deletedAt;
  private Long deletedBy;
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
  private Double installStatus;
  private Double onoffStatus;
  private String remark;
  private Long houseCatalog;
  private Long apartmentId;
  private Long floor;
  private Long houseId;
  private Long roomId;

}
