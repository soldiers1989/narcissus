package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class VersionContent implements Serializable{

  private long id;
  private String contentType;
  private String detail;
  private long versionId;

}
