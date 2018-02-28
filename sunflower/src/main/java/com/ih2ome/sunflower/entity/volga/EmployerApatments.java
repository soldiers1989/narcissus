package com.ih2ome.sunflower.entity.volga;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmployerApatments implements Serializable{

  private long id;
  private long employerId;
  private long userId;

}
