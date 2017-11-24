package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class Room {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String name;
  private double space;
  private String rentStatus;
  private java.sql.Date lockEndTime;
  private String identifier;
  private long edited;
  private String toward;
  private long apartmentId;
  private long floorId;
  private long operatorId;
  private java.sql.Date canRentDate;
  private double price;
  private String roomDesc;
  private long roomType;
  private long assetsId;
  private long channelStatus;
  private String remark;
  private long decoration;
  private String picturesOrder;

}
