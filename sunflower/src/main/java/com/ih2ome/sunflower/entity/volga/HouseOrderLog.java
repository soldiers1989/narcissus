package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class HouseOrderLog implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long userId;
  private String operateName;
  private java.sql.Timestamp operateAt;
  private String old;
  private String news;
  private String change;
  private String remark;
  private long houseOrderId;

}
