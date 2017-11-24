package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class VipSaleRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double amount;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userId;

}
