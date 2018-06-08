package com.ih2ome.sunflower.vo.pageVo.watermeter;

import lombok.Data;

/**
 * @author Hunter Pan
 * create by 2018/5/24
 * @Emial hunter.pan@ixiaoshuidi.com
 */
@Data
public class RecordVO {
    private String date;
    private Integer initial;
    private Integer last;
    private Integer used;
    private Double amount;
}
