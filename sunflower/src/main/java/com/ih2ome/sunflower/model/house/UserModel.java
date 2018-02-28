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
public class UserModel {
    private String userId;
    private String username;
    private List<HomeModel> homeModeList;
}
