package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthtokenToken implements Serializable{

  private String key;
  private java.sql.Timestamp created;
  private long userId;

}
