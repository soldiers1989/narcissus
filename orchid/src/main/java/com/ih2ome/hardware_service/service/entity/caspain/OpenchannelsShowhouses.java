package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class OpenchannelsShowhouses implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long channel;
  private String platformId;
  private long createdById;
  private long deletedById;
  private long houseId;
  private long updatedById;

}
