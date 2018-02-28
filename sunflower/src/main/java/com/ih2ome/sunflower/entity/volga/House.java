package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class House implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long isConcentrated;
  private String city;
  private String district;
  private String block;
  private String address;
  private String area;
  private String buildingNum;
  private String unitNum;
  private String floorNum;
  private String houseNum;
  private long payMethodF;
  private long payMethodY;
  private double monthRental;
  private String rentStatus;
  private long isWhole;
  private long isNew;
  private long isDecorating;
  private java.sql.Date decoratingStartAt;
  private java.sql.Date decoratingEndAt;
  private double space;
  private double price;
  private long isShow;
  private long roomNum;
  private long assetsId;
  private long houseTypeId;

}
