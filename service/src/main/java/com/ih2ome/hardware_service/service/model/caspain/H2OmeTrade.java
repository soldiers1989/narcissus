package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class H2OmeTrade {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String tradeId;
  private java.sql.Timestamp tradeAt;
  private double amount;
  private long tradeType;
  private long feeType;
  private long tradeStatus;
  private String thirdId;
  private String customerName;
  private long payWay;
  private long withdrawalStatus;
  private long createdById;
  private long deletedById;
  private long landlordId;
  private long updatedById;
  private String customerPhone;
  private String houseInfo;
  private long masterId;
  private String landlordName;
  private String comment;
  private double oughtPayAmount;
  private long resource;
  private java.sql.Date endTime;
  private long orderSource;
  private long roomOrderId;
  private java.sql.Date startTime;
  private String source;
  private long houseId;
  private long roomId;
  private long flowType;

}
