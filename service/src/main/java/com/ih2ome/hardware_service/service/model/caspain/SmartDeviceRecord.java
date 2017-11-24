package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class SmartDeviceRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double num;
  private long createdById;
  private long deletedById;
  private long powerMeterId;
  private long updatedById;
  private java.sql.Timestamp readDate;
  private long resource;
  private double publicNum;
  private long isError;

}
