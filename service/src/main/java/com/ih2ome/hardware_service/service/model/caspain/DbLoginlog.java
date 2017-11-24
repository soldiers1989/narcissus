package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class DbLoginlog {

  private long id;
  private java.sql.Timestamp loginAt;
  private long userId;


}
