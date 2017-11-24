package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class PagePms {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String pageCode;
  private String remark;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
