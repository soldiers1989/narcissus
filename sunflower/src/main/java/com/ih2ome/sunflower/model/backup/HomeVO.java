package com.ih2ome.sunflower.model.backup;

import lombok.Data;

import java.util.List;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class HomeVO {
    private String userId;
    private String homeId;
    private String thirdHomeId;
    private String localHomeId;
    private String homeName;
    //判断是集中还是分散(0是集中，1是分散)
    private String homeType;
    private List<RoomVO> rooms;
}
