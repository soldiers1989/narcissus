package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/5/25
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class SmartWatermeterAccountLog {
    private int id;
    private int roomId;
    private int houseCatalog;
    private int amount;
    private int balanceBefore;
    private int balanceAfter;
    private String createdAt;
    private String action;
    private String actionId;
    private String payChannel;
}
