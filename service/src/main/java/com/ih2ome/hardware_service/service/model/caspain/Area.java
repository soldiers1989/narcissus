package com.ih2ome.hardware_service.service.model.caspain;


import lombok.Data;

@Data
public class Area {

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String name;
  private String namePinyin;
  private String address;
  private String url;
  private String district;
  private String city;
  private String province;
  private long createdById;
  private long deletedById;
  private long updatedById;
  private String block;

}
