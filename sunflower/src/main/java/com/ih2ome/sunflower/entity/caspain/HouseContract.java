package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseContract implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String status;
  private java.sql.Date startTime;
  private java.sql.Date endTime;
  private java.sql.Date actualEndTime;
  private String freeDays;
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
  private long createdById;
  private long deletedById;
  private long houseId;
  private long updatedById;
  private String number;
  private long renewTimes;
  private String comments;
  private long genedOrders;
  private String freeTime;
  private long fixedPayDate;
  private String rentPayWay;
  private java.sql.Date ignoreTime;
  private String evictionReason;

}
