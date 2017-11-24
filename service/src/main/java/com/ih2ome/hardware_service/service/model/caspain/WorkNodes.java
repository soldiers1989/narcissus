package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class WorkNodes {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long createdById;
  private long updatedById;
  private long deletedById;
  private long version;
  private long isDelete;
  private long workBenchId;
  private String name;
  private long step;
  private String approveUserIds;

}
