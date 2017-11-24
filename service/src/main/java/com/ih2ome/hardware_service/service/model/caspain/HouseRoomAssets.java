package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class HouseRoomAssets {

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
