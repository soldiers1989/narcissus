package com.ih2ome.sunflower.entity.narcissus;

/**
 * @author Sky
 * @create 2018/02/06
 * @email sky.li@ixiaoshuidi.com
 **/
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
    private String createdBy;
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
     (1:新增密码
     2:更新密码
     3:删除密码
     4:冻结密码
     5:解冻密码)
     */
    private Long operatorType;
}
