package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthUserUserPermissions implements Serializable{

  private long id;
  private long userId;
  private long permissionId;

}
