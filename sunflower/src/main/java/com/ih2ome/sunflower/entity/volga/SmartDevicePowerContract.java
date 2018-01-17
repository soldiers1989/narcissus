package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartDevicePowerContract implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private double initNum;
  private double publicInitNum;
  private java.sql.Timestamp intNumDate;
  private double prepaymentMoney;
  private String charge;
  private double initMoney;
  private long isPrepower;
  private double prePrepowerMoney;
  private long contractId;
  private long powerMeterId;

}
