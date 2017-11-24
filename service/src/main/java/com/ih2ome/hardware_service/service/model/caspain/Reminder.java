package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class Reminder {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private String content;
  private java.sql.Date deadlineTime;
  private long isDone;
  private long userId;

}
