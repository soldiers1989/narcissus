package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseDepositorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private double amount;
  private java.sql.Timestamp generateTime;
  private java.sql.Timestamp actualPayTime;
  private long payStatus;
  private String comments;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private double amountReturn;
  private double rentamountAppend;
  private double rentamountReturn;
  private String uuid;

}
