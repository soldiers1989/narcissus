package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class Todo implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdBy;
  private long updatedBy;
  private long deletedBy;
  private long version;
  private long isDelete;
  private long target;
  private long category;
  private long isDone;
  private java.sql.Date deadline;
  private String column1;
  private String column2;
  private String column3;
  private String column4;
  private String column5;
  private String column6;
  private String column7;
  private String column8;
  private String column9;

}
