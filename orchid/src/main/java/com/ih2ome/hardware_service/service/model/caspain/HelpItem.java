package com.ih2ome.hardware_service.service.model.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class HelpItem implements Serializable{

  private long id;
  private String name;
  private String title;
  private String url;

}
