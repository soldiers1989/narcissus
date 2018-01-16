package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class VersionContent implements Serializable{

  private long id;
  private String contentType;
  private String detail;
  private long versionId;

}
