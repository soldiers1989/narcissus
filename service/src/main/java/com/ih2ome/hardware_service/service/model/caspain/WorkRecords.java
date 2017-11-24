package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class WorkRecords {

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
  private long workNodeId;
  private long workApprovedId;
  private long workType;
  private String workNodeName;
  private long step;
  private long status;
  private String remark;
  private String operator;

}
