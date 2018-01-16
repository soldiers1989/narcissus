package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseDepositorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String uuid;
  private double amount;
  private double amountReturn;
  private double rentamountReturn;
  private double rentamountAppend;
  private java.sql.Timestamp generateTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private long contractId;

}
