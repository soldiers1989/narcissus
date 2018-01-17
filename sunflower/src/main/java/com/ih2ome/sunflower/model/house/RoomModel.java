package com.ih2ome.sunflower.model.house;


import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/17
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class RoomModel {

    /**
     * 房源id
     */
    private String roomId;
    /**
     * 楼层
     */
    private String floorNum;
    /**
     * 公寓名或小区
     */
    private String apartmentName;
    /**
     * 行政区、行政县
     */
    private String blockName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 房源地址
     */
    private String address;

}
