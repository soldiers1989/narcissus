package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Employer implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String phone;
  private String remark;
  private long bossId;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userId;
  private long roleId;
  private String accountType;

}
