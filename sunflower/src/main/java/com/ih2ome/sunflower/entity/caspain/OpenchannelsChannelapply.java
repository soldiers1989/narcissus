package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class OpenchannelsChannelapply implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private long userId;
  private String province;
  private String city;
  private String name;
  private String legal;
  private String companyIdCard;
  private String companyName;
  private String brandName;
  private String phone;
  private long status;
  private String openKey;
  private String appid;
  private long operateMode;
  private long roomCount;
  private String wubaUserName;

}
