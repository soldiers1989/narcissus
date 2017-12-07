package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Announcement implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String title;
  private String content;
  private long status;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long tag;
  private long pushChoices;
  private java.sql.Timestamp pushTime;

}
