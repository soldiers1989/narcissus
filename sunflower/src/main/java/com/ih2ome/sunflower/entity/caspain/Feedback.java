package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Feedback implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String title;
  private String detail;
  private String pictures;
  private long isDone;
  private String result;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
