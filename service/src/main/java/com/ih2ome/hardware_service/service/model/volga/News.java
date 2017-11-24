package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class News {

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
  private long readn;
  private String column1;
  private String column2;
  private String column3;
  private String column4;
  private String column5;
  private String column6;

}
