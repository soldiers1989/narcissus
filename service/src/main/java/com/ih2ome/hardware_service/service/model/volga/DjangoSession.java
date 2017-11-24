package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

@Data
public class DjangoSession {

  private String sessionKey;
  private String sessionData;
  private java.sql.Timestamp expireDate;

}
