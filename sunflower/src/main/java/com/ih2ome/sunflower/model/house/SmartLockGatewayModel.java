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
public class SmartLockGatewayModel {
    private String gatewayId;
    private String thirdGatewayId;
    private RoomModel roomModel;
}
