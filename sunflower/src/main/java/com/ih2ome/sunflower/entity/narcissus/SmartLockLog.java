package com.ih2ome.sunflower.entity.narcissus;

import lombok.Data;

/**
 * @author Sky
 * @create 2018/02/06
 * @email sky.li@ixiaoshuidi.com
 **/
@Data
public class SmartLockLog {
    /**
     * 操作记录id
     */
    private Long smartLockLogId;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 创建者
     */
    private Long createdBy;
    /**
     * 门锁操作密码id
     */
    private Long smartLockPasswordId;
    /**
     * 门锁id
     */
    private Long smartLockId;
    /**
     * 操作类型
     * (1:新增密码
     * 2:更新密码
     * 3:删除密码
     * 4:冻结密码
     * 5:解冻密码)
     */
    private Long operatorType;

    public SmartLockLog(Long createdBy, Long smartLockPasswordId, Long smartLockId, Long operatorType) {
        this.createdBy = createdBy;
        this.smartLockPasswordId = smartLockPasswordId;
        this.smartLockId = smartLockId;
        this.operatorType = operatorType;
    }

    public SmartLockLog() {
    }
}
