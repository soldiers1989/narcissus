package com.ih2ome.hardware_service.service.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthPermission implements Serializable{

  private long id;
  private String name;
  private long contentTypeId;
  private String codename;

}
