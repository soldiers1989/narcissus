package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

import java.io.Serializable;
@Data
public class SmartAlarmRule implements Serializable{

  private Long id;
  private String reportName;
  private String reportParam;
  private String switchStatus;

}