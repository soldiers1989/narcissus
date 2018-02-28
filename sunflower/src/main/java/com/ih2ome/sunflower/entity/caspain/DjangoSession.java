package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class DjangoSession implements Serializable{

  private String sessionKey;
  private String sessionData;
  private java.sql.Timestamp expireDate;

}
