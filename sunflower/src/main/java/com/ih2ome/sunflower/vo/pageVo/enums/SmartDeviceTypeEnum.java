package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum SmartDeviceTypeEnum {
    POWER_BEE_AMMETER("蜂电电表",0L),
    YUN_DING_WATERMETER_GATEWAY("云丁水表网关",5L),
    YUN_DING_WATERMETER("云丁水表",2L),
    YUN_DING_SMART_LOCK("云丁门锁",3L),
    YUN_DING_SMART_LOCK_GATEWAY("云丁门锁网关",4L);

    private String name;
    private Long code;

    SmartDeviceTypeEnum(String name, Long code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public static SmartDeviceTypeEnum getByCode(Integer code) {
        for (SmartDeviceTypeEnum smartDeviceType : SmartDeviceTypeEnum.values()) {
            if (smartDeviceType.getCode().equals(code)) {
                return smartDeviceType;
            }
        }
        return null;
    }
}
