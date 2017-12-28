package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

/**
 * @author Sky
 * @create 2017/12/28
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockRequestVo extends BaseEntity  {
    //集中分散类型
    private String type;
    //门锁编码
    private String lockNo;
}
