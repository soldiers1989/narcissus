package com.ih2ome.hardware_service.service.enums;

/**
 * <br>
 *
 * @author Lucius
 *         create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum HomeSyncEnum {

   HOME_SYNC_YUNDING("云丁", 1);



    private String name;
    private Integer code;

    public static HomeSyncEnum getByCode(Integer code) {
        for (HomeSyncEnum alarmTypeEnum : HomeSyncEnum.values()) {
            if (alarmTypeEnum.getCode().equals(code)) {
                return alarmTypeEnum;
            }
        }
        return null;
    }


    HomeSyncEnum(String name, Integer code) {
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
