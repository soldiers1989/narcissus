package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartDevice implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long brand;
  private long style;
  private String uuid;
  private String serialId;
  private String name;
  private String master;
  private long status;
  private long bind;
  private double price;
  private long createdById;
  private long deletedById;
  private long houseId;
  private long roomId;
  private long updatedById;
  private long online;
  private long isShare;
  private double lastNum;
  private long share;
  private java.sql.Timestamp lastNumDate;
  private long isAutoPowerOff;
  private long wiring;

}
