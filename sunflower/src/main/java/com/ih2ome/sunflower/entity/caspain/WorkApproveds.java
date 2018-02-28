package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class WorkApproveds implements Serializable{

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
  private long status;
  private String data;
  private String remark;
  private long relatedObjId;

}
