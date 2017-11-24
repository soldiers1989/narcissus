package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class UserApplication {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String phone;
  private String company;
  private String name;
  private long status;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private long userId;

}
