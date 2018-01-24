package com.ih2ome.sunflower.model.house;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class RoomModel {
    private String roomId;
    private String floor;
    private HomeModel homeModel;
    private String roomName;
    private String thirdRoomId;
    private String thirdRoomName;
}
