package com.ih2ome.peony.smartlockInterface.vo;

import lombok.Data;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/17
 * @Emial Lucius.li@ixiaoshuidi.com
 */
@Data
public class HouseVO {
    /**
     * 房源id
     */
    private String houseId;
    /**
     * 楼层
     */
    private String floor;
    /**
     * 小区名或公寓名
     */
    private String area;
    /**
     * 行政区、行政县
     */
    private String block;
    /**
     * 市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 房源地址
     */
    private String address;
}
