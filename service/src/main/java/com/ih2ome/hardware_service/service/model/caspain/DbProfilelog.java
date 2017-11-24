package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class DbProfilelog {

  private long id;
  private String tableName;
  private String columnName;
  private String oldValue;
  private String newValue;
  private java.sql.Timestamp createdAt;
  private long userId;

}
