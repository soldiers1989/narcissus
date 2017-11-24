package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class UserLog {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private java.sql.Timestamp operateAt;
  private String url;
  private String urlDesc;
  private String method;
  private String level;
  private long createdById;
  private long deletedById;
  private long landlordId;
  private long updatedById;
  private long userId;

}
