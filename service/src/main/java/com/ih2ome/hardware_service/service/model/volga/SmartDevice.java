package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class SmartDevice {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long brand;
  private long style;
  private String uuid;
  private String serialId;
  private String name;
  private String master;
  private long status;
  private long online;
  private long bind;
  private double price;
  private long apartmentId;
  private long roomId;
  private long floor;
  private long isShare;
  private double lastNum;
  private java.sql.Timestamp lastNumDate;
  private long share;
  private long isAutoPowerOff;
  private long wiring;

}
