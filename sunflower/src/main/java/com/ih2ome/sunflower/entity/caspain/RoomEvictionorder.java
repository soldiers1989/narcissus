package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomEvictionorder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String code;
  private long depositType;
  private double deposit;
  private java.sql.Date endTime;
  private long evictionType;
  private long payStatus;
  private long cleanOrders;
  private String comments;
  private long contractId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private String uuid;

}
