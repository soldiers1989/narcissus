package com.ih2ome.sunflower.entity.caspain;

import lombok.Data;

import java.io.Serializable;
@Data
public class RoomPictures implements Serializable{

  private long id;
  private long roomId;
  private long pictureId;

}
