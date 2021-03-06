package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartLockGateway implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private String gateway;
  private long status;
  private long roomId;
  private long houseId;
  private long installed;
  private java.sql.Timestamp installTime;
  private String installerName;
  private String installerMobile;
  private String operator;
  private String remark;
  private String rdata;
  private java.sql.Timestamp rtime;

}
