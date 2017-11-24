package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class HouseContract {

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
  private String roomComments;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private java.sql.Date actualEndTime;
  private String freeDays;
  private String freeTime;
  private long advancedDays;
  private double deposit;
  private String payMethod;
  private long payMethodF;
  private long payMethodY;
  private double monthRental;
  private long yearlyGrowthRate;
  private long yearlyGrowthAmount;
  private String ownerName;
  private String ownerPhone;
  private String ownerIdType;
  private String ownerIdNumber;
  private String number;
  private long renewTimes;
  private String comments;
  private long genedOrders;
  private long apartmentId;
  private java.sql.Date ignoreTime;
  private String rentPayWay;
  private long fixedPayDate;
  private String evictionReason;

}
