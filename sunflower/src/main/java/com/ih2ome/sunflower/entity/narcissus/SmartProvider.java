package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartProvider implements Serializable{

  private Long smartProviderId;
  private java.sql.Timestamp createdAt;
  private Long createdBy;
  private java.sql.Timestamp updatedAt;
  private Long updatedBy;
  private java.sql.Timestamp deletedAt;
  private Long deletedBy;
  private String name;

}
