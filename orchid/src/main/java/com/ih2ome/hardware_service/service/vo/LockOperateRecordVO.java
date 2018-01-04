package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 门锁的操作记录
 *
 * @author Sky
 * @create 2018/01/03
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockOperateRecordVO extends BaseEntity implements Serializable {
    private String id;
    //操作类型
    private String operateType;
    //操作时间
    private String operateTime;
    //使用人手机
    private String mobile;
    //0集中，1分散
    private String type;
    //门锁编码
    private String serialNum;
}
