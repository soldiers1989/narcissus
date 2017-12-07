package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartLockRecord implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private long lockId;
  private String remarksName;
  private String mobile;
  private long passwordType;
  private java.sql.Timestamp unlockTime;
  private String rdata;
  private java.sql.Timestamp rtime;

}
