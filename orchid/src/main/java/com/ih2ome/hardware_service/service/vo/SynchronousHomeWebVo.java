package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/28
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class SynchronousHomeWebVo implements Serializable {
    private String homeId;//房源id
    private String homeName;//房源名称
    private String synchronous;//是否已同步,0或null未同步,1.同步至云丁
    private String authUserName;//用户名(房东手机号)
    private String provinceName;//省名
    private String cityName;//市名
    private String districtName;//区名
    private String areaName;//小区名
    private String houseAddress;//房源地址
    private String type;//集中式或分散式

//    private List<HmRoomSyncVO> hmRoomSyncVOList;//room同步状态
}
