package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartLock implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private long roomId;
  private long gatewayId;
  private long lockType;
  private String serialNum;
  private long status;
  private double remainingBattery;
  private long installed;
  private java.sql.Timestamp installTime;
  private String installerName;
  private String installAddr;
  private String installerMobile;
  private String operator;
  private String remark;
  private String rdata;
  private java.sql.Timestamp rtime;

}
