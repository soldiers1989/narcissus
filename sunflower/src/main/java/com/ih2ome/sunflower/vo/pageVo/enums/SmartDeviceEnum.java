package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/01/30
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartDeviceEnum {
    SMART_DEVICE_AMMETER("智能电表", "1"),
    SMART_DEVICE_WATERMETER("智能水表", "2"),
    SMART_DEVICE_LOCK("智能门锁", "3"),
    SMART_DEVICE_GATEWAY("智能网关", "4");

    private String name;
    private String code;

    SmartDeviceEnum(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
