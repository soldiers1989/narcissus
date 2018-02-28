package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomContractRooms implements Serializable{

  private long id;
  private long roomcontractId;
  private long roomId;

}
