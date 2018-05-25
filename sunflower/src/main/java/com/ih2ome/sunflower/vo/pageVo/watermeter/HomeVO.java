package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/5/23
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class HomeVO {
    /**
     * 房源Id（公寓Id 或 房屋Id）
     */
    private int id;

    /**
     * 房源名称
     */
    private String name;

    /**
     * 房源类型：apartment / house
     */
    private String type;
}
