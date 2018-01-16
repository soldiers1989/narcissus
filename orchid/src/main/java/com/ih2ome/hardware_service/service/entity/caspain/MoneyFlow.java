package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class MoneyFlow implements Serializable{

  private long id;
  private String uuid;
  private java.sql.Timestamp createdAt;
  private long isDelete;
  private long flowType;
  private long feeType;
  private double realAmount;
  private double oughtAmount;
  private String tradeNo;
  private String tradeCode;
  private long payMethod;
  private long sourceFrom;
  private String orderUuid;
  private long isWholeHouse;
  private java.sql.Timestamp tradeAt;
  private String traderName;
  private String remark;
  private long createdById;
  private long houseId;
  private long roomId;
  private java.sql.Timestamp deletedAt;
  private long deletedById;
  private long deletedType;
  private String tradeSerialNo;
  private String transactionId;

}
