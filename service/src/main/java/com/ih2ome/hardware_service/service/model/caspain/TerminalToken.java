package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class TerminalToken {

  private String key;
  private java.sql.Timestamp created;
  private String terminal;
  private long userId;

}
