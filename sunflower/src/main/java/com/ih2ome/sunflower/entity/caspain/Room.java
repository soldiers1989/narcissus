package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Room implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private double space;
  private String rentStatus;
  private java.sql.Date lockEndTime;
  private String identifier;
  private long edited;
  private double price;
  private long assetsId;
  private long createdById;
  private long deletedById;
  private long houseId;
  private long updatedById;
  private long payMethodF;
  private long payMethodY;
  private String toward;
  private java.sql.Date canRentDate;
  private String roomDesc;
  private long roomType;
  private long channelStatus;
  private String remark;
  private long decoration;
  private String picturesOrder;

}
