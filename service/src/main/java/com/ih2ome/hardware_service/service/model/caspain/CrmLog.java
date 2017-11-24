package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class CrmLog {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private java.sql.Timestamp operatedAt;
  private String action;
  private String actionType;
  private String comments;
  private String oldStatus;
  private String newStatus;
  private long createdById;
  private long customerResId;
  private long deletedById;
  private long newOperatorId;
  private long oldOperatorId;
  private long operateById;
  private long updatedById;

}
