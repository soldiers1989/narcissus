package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Version implements Serializable{

  private long id;
  private String terminal;
  private String versionBranch;
  private String versionSub;
  private String name;
  private String detail;
  private String downloadUrl;
  private long forceUpdate;
  private java.sql.Timestamp releaseTime;

}
