package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserIdauth implements Serializable{

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
  private long idauthType;
  private long idauthStatus;
  private String name;
  private String province;
  private String city;
  private String phone;
  private String brandName;
  private long roomCount;
  private String pictures;
  private String legal;
  private String companyIdCard;
  private String companyName;
  private long operateMode;

}
