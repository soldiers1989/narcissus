package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

@Data
public class SmartProviderV2 {

  private long smartProviderId;
  private java.sql.Timestamp createdAt;
  private long createdBy;
  private String updatedAt;
  private long updatedBy;
  private java.sql.Timestamp deletedAt;
  private long deletedBy;
  private String name;
  private long code;

}
