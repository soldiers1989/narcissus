package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerRes implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String sex;
  private String phone;
  private long source;
  private java.sql.Date wantedDay;
  private String address;
  private String minRent;
  private String maxRent;
  private long headcount;
  private String houseType;
  private String customerType;
  private String operatorName;
  private String comments;
  private long status;
  private long createdById;
  private long deletedById;
  private long operatorId;
  private long updatedById;
  private long length;
  private java.sql.Timestamp bookTime;
  private long urgency;
  private java.sql.Timestamp operatedAt;
  private long roomId;
  private long roomSource;

}
