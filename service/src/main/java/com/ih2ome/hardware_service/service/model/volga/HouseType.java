package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class HouseType {

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
  private long bedrooms;
  private long livingrooms;
  private long washrooms;
  private long oldId;

}
