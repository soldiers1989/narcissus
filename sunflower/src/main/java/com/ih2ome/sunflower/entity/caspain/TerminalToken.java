package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class TerminalToken implements Serializable{

  private String key;
  private java.sql.Timestamp created;
  private String terminal;
  private long userId;

}
