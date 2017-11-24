package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class SmartDeviceRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private double num;
  private long powerMeterId;
  private double publicNum;
  private java.sql.Timestamp readDate;
  private long resource;
  private long isError;

}
