package com.ih2ome.sunflower.model.house;

import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/24
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class HomeModel {
    private String homeId;
    private UserModel userModel;
    private String homeType;
    private String homeName;
    private String thirdHomeId;
    private String thirdHomeName;
    private String block;
    private String city;
    private String address;
    private String province;
    private List<RoomModel> roomModelList;
}
