package com.ih2ome.hardware_service.service.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum SmartDeviceTypeEnum {
    POWER_BEE_AMMETER("蜂电电表",0L);
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
