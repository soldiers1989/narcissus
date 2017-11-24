package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class RegistrationId {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String registrationId;
  private long userId;
  private long systemNotification;
  private long noticeAndMorningreading;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
