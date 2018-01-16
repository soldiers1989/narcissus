package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class OpenchannelsShowrooms implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long channel;
  private long status;
  private String houseId;
  private String extra;
  private String rawData;
  private String errMsg;
  private long roomId;
  private String apartRoomId;

}
