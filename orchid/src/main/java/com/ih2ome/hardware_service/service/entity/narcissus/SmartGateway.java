package com.ih2ome.hardware_service.service.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SmartGateway implements Serializable{

  private Long smartGatewayId;
  private Date createdAt;
  private Long createdBy;
  private Date updatedAt;
  private Long updatedBy;
  private Date deletedAt;
  private Long deletedBy;
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
  private int installStatus;
  private int onoffStatus;
  private String remark;
  private Long houseCatalog;
  private Long apartmentId;
  private int floor;
  private Long houseId;
  private Long roomId;

}
