package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class DjangoAdminLog implements Serializable{

  private long id;
  private java.sql.Timestamp actionTime;
  private String objectId;
  private String objectRepr;
  private long actionFlag;
  private String changeMessage;
  private long contentTypeId;
  private long userId;

}
