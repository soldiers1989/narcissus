package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DbLoginlog implements Serializable{

  private long id;
  private java.sql.Timestamp loginAt;
  private long userId;

}
