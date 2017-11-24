package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class DjangoAdminLog {

  private long id;
  private java.sql.Timestamp actionTime;
  private String objectId;
  private String objectRepr;
  private long actionFlag;
  private String changeMessage;
  private long contentTypeId;
  private long userId;

}
