package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DbActionlog implements Serializable{

  private long id;
  private long action;
  private java.sql.Timestamp createdAt;
  private long userId;

}
