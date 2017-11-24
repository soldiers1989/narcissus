package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class DbActionlog {

  private long id;
  private long action;
  private java.sql.Timestamp createdAt;
  private long userId;

}
