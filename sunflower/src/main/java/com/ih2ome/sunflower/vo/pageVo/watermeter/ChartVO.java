package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

import java.util.Date;

/**
 * @author Hunter Pan
 * create by 2018/5/28
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class ChartVO {
    private String start;
    private String end;
    private Long initial;
    private Long last;
    private Long used;
    private Double amount;
}
