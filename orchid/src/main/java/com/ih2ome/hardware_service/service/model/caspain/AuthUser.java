package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthUser implements Serializable{

  private long id;
  private String password;
  private java.sql.Timestamp lastLogin;
  private long isSuperuser;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private long isStaff;
  private long isActive;
  private java.sql.Timestamp dateJoined;

}
