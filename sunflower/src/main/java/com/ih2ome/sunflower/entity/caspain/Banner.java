package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Banner implements Serializable{

  private long id;
  private java.sql.Timestamp createdAt;
  private java.sql.Timestamp updatedAt;
  private java.sql.Timestamp deletedAt;
  private long version;
  private long isDelete;
  private String imgUrl;
  private String describe;
  private String targetUrl;
  private long order;
  private long createdById;
  private long deletedById;
  private long updatedById;

}
