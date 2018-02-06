package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * <br>
 *
 * @author Lucius
 *         create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum  AlarmTypeEnum {

    POWER_RATE_SMALL_THAN_ZERO("电费出现负数", 0),
    POWER_CONSUMPTION_WITHOUT_CHECKIN("未办理入住，有用电支出", 1),
    DATA_IS_NOT_UPDATE("数据未更新", 2),
    LONG_TIME_OFF_LINE("长时间设备离线", 3),
    POWER_NOT_FAILURE_WITH_POWER_RATE_THAN_ZERO("负数不断电", 4),
    YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_OFF_LINE("水表网关设备离线",5),
    YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_ON_LINE("水表网关设备在线",6),
    YUN_DING_WATERMETER_EXCEPTION_TYPE_OFF_LINE("水表设备离线",7),
    YUN_DING_WATERMETER_EXCEPTION_TYPE_ON_LINE("水表设备在线",8),
    YUN_DING_SMART_LOCK_EXCEPTION_TYPE_LOWER_POWER("设备低电量报警",9),
    YUN_DING_SMART_LOCK_EXCEPTION_TYPE_LOWER_POWER_RECOVER("设备电量恢复",10),
    YUN_DING_SMART_LOCK_EXCEPTION_TYPE_BROKEN("门锁被破坏报警",11),
    YUN_DING_SMART_LOCK_EXCEPTION_WRONG_PWD("门锁密码输错三次报警",12),
    YUN_DING_SMART_LOCK_OPEN("门锁开门记录",13),
    YUN_DING_SMAR_LOCK_GATEWAY_OFFLINE("网关离线记录",14),
    YUN_DING_SMAR_LOCK_GATEWAY_ONLINE("网关上线记录",15),
    YUN_DING_SMAR_LOCK_ONLINE("门锁上线记录",16),
    YUN_DING_SMAR_LOCK_OFFLINE("门锁离线记录",17),
    YUN_DING_SMART_LOCK_ADD_PASSWORD("添加密码",18),
    YUN_DING_SMART_LOCK_UPDATE_PASSWORD("更新密码",19),
    YUN_DING_SMART_LOCK_DELETE_PASSWORD("删除密码",20),
    YUN_DING_SMART_LOCK_FROZEN_PASSWORD("冻结密码",21);


    private String name;
    private Integer code;

    public static AlarmTypeEnum getByCode(Integer code) {
        for (AlarmTypeEnum alarmTypeEnum : AlarmTypeEnum.values()) {
            if (alarmTypeEnum.getCode().equals(code)) {
                return alarmTypeEnum;
            }
        }
        return null;
    }


    AlarmTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
