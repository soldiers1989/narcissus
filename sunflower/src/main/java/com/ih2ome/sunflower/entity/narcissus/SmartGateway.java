package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.util.Date;

@Data
public class SmartGateway {

  private long smartGatewayId;
  private Date createdAt;
  private long createdBy;
  private Date updatedAt;
  private long updatedBy;
  private Date deletedAt;
  private long deletedBy;
  private String mac;
  private String sn;
  private String uuid;
  private String model;
  private String modelName;
  private String name;
  private Date installTime;
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
