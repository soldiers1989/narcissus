package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ShuididaiCreditsourcedata implements Serializable{

  private long id;
  private java.sql.Date housecontractStartTime;
  private java.sql.Date housecontractEndTime;
  private double housecontractRental;
  private double roomcontractRental;
  private long hasHousecontract;
  private long hasRoomcontract;
  private long houseId;
  private long userId;

}
