package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderFlow implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private long isDelete;
  private long flowType;
  private long feeType;
  private double amount;
  private long isWholeHouse;
  private java.sql.Timestamp tradeAt;
  private String traderName;
  private String remark;
  private long createdById;
  private long houseId;
  private long roomId;

}
