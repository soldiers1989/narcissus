package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class DjangoMigrations {

  private long id;
  private String app;
  private String name;
  private java.sql.Timestamp applied;

}
