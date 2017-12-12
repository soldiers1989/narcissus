package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class Payment implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String transactionId;
  private String orderId;
  private long userId;
  private String tradeInfo;
  private String tradeObject;
  private String traderName;
  private String tradeSerialNo;
  private java.sql.Timestamp tradeAt;
  private String payMethod;
  private double amount;
  private long flowType;
  private String comments;
  private String houseId;

}