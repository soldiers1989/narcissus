package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomContract implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String status;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private java.sql.Date actualEndTime;
  private double deposit;
  private String payMethod;
  private long payMethodF;
  private long payMethodY;
  private long freeDays;
  private long advancedDays;
  private String customerName;
  private String customerPhone;
  private String customerIdType;
  private String customerIdNumber;
  private String number;
  private long renewTimes;
  private String comments;
  private long genedOrders;
  private long apartmentId;
  private long roomId;
  private long fixedPayDate;
  private String rentPayWay;
  private long installmentNum;
  private long isInstallment;
  private long isLoan;
  private double loanAmount;
  private String loanContractId;
  private String sceneCustId;
  private java.sql.Date ignoreTime;
  private String evictionReason;
  private long signed;
  private java.sql.Date signedAt;
  private String signedPicture;

}
