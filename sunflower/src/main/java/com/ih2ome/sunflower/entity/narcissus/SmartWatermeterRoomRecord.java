package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/6/6
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class SmartWatermeterRoomRecord {
    private Integer id;
    private Integer roomId;
    private Integer houseCatalog;
    private Integer meterType;
    private Integer deviceAmount;
    private Boolean isInit;
    private String createdAt;
    private Integer waterId;
}
