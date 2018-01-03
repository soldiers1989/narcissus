package com.ih2ome.hardware_service.service.vo;

import com.ih2ome.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sky
 * @create 2018/01/03
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class LockOpenRecordVO extends BaseEntity implements Serializable {
    //门锁编号
    private String serialNum;
    //开门人姓名
    private String userName;
    //开门人手机
    private String mobile;
    //开门密码类型
    private String passwordType;
    //开锁时间
    private String unlockTime;

    private String type;
}
