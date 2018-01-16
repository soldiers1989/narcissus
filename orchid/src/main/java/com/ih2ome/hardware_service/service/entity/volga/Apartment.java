package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class Apartment implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private String name;
  private String city;
  private String district;
  private String block;
  private String address;
  private long isNew;
  private long isDecorating;
  private java.sql.Date decoratingStartAt;
  private java.sql.Date decoratingEndAt;
  private long floorNum;
  private long floorRoomNum;
  private long roomNum;
  private long operatorId;
  private String area;
  private String remark;
  private String latitude;
  private String longitude;

}
