package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseRoomAssets implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private long televisionNum;
  private long airConditioningNum;
  private long washingMachineNum;
  private long wardrobeNum;
  private long deskNum;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
