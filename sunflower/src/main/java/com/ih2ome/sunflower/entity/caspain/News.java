package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class News implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private long target;
  private long category;
  private long readn;
  private String column1;
  private String column2;
  private String column3;
  private String column4;
  private String column5;
  private String column6;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
