package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class WxPublic implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String weixin;
  private String appId;
  private String appSecret;
  private String token;
  private String aesKey;
  private String uuid;
  private long available;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userprofileId;
  private String createMenu;
  private String menuBills;
  private String menuStaging;
  private String menuUser;

}
