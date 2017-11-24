package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class RoomDepositorder {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double amount;
  private double amountReturn;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private double rentamountAppend;
  private double rentamountReturn;
  private String uuid;

}
