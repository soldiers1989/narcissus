package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class LandlordRechangeRecord {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private long landlordId;
  private String landlordPhone;
  private java.sql.Timestamp orderTime;
  private String orderNo;
  private String transferCredentials;
  private long rechargeWay;
  private double rechargeMoney;
  private String rechargePhone;
  private long status;

}
