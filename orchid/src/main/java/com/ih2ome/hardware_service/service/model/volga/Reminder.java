package com.ih2ome.hardware_service.service.model.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class Reminder implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private long user;
  private String content;
  private java.sql.Date deadlineTime;
  private long isDone;
  private long createdBy;
  private long operatorId;

}
