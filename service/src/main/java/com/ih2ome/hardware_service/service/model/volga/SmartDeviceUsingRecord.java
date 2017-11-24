package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class SmartDeviceUsingRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private double usingMoney;
  private double publicMoney;
  private double totalMoney;
  private double remainMoney;
  private String remark;
  private long powerMeterId;
  private long contractId;
  private double usingBefore;

}
