package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DbProfilelog implements Serializable{

  private long id;
  private String tableName;
  private String columnName;
  private String oldValue;
  private String newValue;
  private java.sql.Timestamp createdAt;
  private long userId;

}
