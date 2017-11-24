package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class House {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
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
  private double space;
  private double price;
  private long assetsId;
  private long createdById;
  private long deletedById;
  private long houseTypeId;
  private long updatedById;
  private long isNew;
  private java.sql.Date decoratingEndAt;
  private java.sql.Date decoratingStartAt;
  private long isDecorating;
  private long isShow;
  private long isConcentrated;
  private long roomNum;
  private String totalFloorNum;
  private long isStop;
  private double decoratingFee;
  private String remark;

}
