package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class WxPublic {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
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
  private String menuBills;
  private String menuUser;
  private String menuStaging;
  private String createMenu;
  private long userprofileId;

}
