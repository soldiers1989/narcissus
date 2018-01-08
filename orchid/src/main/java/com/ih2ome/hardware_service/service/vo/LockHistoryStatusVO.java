package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 门锁历史状态
 *
 * @author Sky
 * @create 2018/01/02
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockHistoryStatusVO extends BaseEntity implements Serializable {
    //主键
    private String id;
    //门锁编码
    private String serialNum;
    //0是集中，1是分散
    private String type;
    //门锁状态
    private String status;
    //果家推过来的数据
    private String rdata;
    //上报时间
    private String reportTime;
}
