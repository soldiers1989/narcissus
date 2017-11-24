package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class SmartDeviceUsingRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double usingMoney;
  private double remainMoney;
  private long createdById;
  private long deletedById;
  private long powerMeterId;
  private long updatedById;
  private String remark;
  private double publicMoney;
  private double totalMoney;
  private long contractId;
  private double usingBefore;

}
