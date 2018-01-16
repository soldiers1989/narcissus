package com.ih2ome.hardware_service.service.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class DjangoSession implements Serializable{

  private String sessionKey;
  private String sessionData;
  private java.sql.Timestamp expireDate;

}
