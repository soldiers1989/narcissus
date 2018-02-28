package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class OpenchannelsShowlayouts implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long floorId;
  private String layoutId;
  private long channel;
  private String outLayoutId;

}
