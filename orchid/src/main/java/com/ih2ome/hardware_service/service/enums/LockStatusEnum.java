package com.ih2ome.hardware_service.service.enums;

/**
 * @author Sky
 * @create 2017/12/28
 * @email sky.li@ixiaoshuidi.com
 **/
public enum LockStatusEnum {
    START_USE("已启用","1"),END_USE("已禁用","0");

    private String name;
    private String code;

    LockStatusEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String getByCode(String code) {
        for (LockStatusEnum lockStatusEnum : LockStatusEnum.values()) {
            if (lockStatusEnum.getCode().equals(code)) {
                return lockStatusEnum.getName();
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
