package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DjangoMigrations implements Serializable{

  private long id;
  private String app;
  private String name;
  private java.sql.Timestamp applied;

}