package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Powerbi implements Serializable{

  private long id;
  private long userId;
  private String city;
  private String url;
  private java.sql.Timestamp createdAt;

}
