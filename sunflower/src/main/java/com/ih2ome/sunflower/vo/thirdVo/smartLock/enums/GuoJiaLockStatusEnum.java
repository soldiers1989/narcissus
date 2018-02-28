package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * 果家推送事件类型
 *
 * @author Sky
 * @create 2018/01/02
 * @email sky.li@ixiaoshuidi.com
 **/
public enum GuoJiaLockStatusEnum {
    //密码设置成功
    PUSH_LOCK_SET_PWD_SUCCESS("PUSH_LOCK_SET_PWD_SUCCESS", "增加密码成功"),
    //密码设置失败
    PUSH_LOCK_SET_PWD_FAIL("PUSH_LOCK_SET_PWD_FAIL", "增加密码失败"),
    //电池电量低
    PUSH_LOCK_POWRE_LOW("PUSH_LOCK_POWRE_LOW", "电量低"),
    //电池电量恢复
    PUSH_LOCK_POWRE_RECOVERY("PUSH_LOCK_POWRE_RECOVERY ", "恢复电量"),
    //开锁提醒
    PUSH_OPEN_LOCK("PUSH_OPEN_LOCK", "开锁"),
    //门锁离线提醒
    PUSH_LOCK_OFFLINE("PUSH_LOCK_OFFLINE", "门锁离线"),
    //门锁在线提醒
    PUSH_LOCK_ONLINE("PUSH_LOCK_ONLINE", "门锁在线"),
    //网关离线提醒
    PUSH_NODE_OFFLINE("PUSH_NODE_OFFLINE", "网关离线"),
    //网关在线提醒
    PUSH_NODE_ONLINE("PUSH_NODE_ONLINE", "网关在线");

    private String status;
    private String statusName;

    public static String getByStatus(String status) {
        for (GuoJiaLockStatusEnum guoJiaLockStatusEnum : GuoJiaLockStatusEnum.values()) {
            if (guoJiaLockStatusEnum.getStatus().equals(status)) {
                return guoJiaLockStatusEnum.getStatusName();
            }
        }
        return null;
    }

    GuoJiaLockStatusEnum(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public static JSONObject enum2Json(){
        JSONObject jsonObject = new JSONObject();
        for (GuoJiaLockStatusEnum guoJiaLockStatusEnum : GuoJiaLockStatusEnum.values()) {
            jsonObject.put(guoJiaLockStatusEnum.getStatus(),guoJiaLockStatusEnum.getStatusName());
        }
        return jsonObject;
    }
}
