package com.ih2ome.hardware_service.service.enums;

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
    YUN_DING_WATERMETER_GATEWAY_EXCEPTION_TYPE_ON_LINE("水表网关设备在线",6);


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
