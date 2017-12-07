package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartLockPassword implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private String password;
  private long passwordType;
  private long digitPwdType;
  private long status;
  private long lockId;
  private java.sql.Timestamp enableTime;
  private java.sql.Timestamp disableTime;
  private String userName;
  private String mobile;
  private String messageContent;
  private String remark;
  private String pwdNo;
  private String rdata;
  private java.sql.Timestamp rtime;

}
