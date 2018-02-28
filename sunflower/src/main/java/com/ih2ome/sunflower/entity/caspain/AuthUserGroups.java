package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthUserGroups implements Serializable{

  private long id;
  private long userId;
  private long groupId;

}
