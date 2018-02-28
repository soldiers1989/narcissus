package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthGroupPermissions implements Serializable{

  private long id;
  private long groupId;
  private long permissionId;

}
