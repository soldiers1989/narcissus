package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/02/02
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockPwdTypeEnum {
    ROOMER_PASSWORD("0", "租客密码"),
    CLEANER_PASSWORD("1", "保洁密码"),
    MANAGER_PASSWORD("2", "管理密码"),
    LOOKER_PASSWORD("3", "带看密码"),
    EXTRA_PASSWORD("4", "扩展密码"),
    OTHER_PASSWORD("5", "其他密码");


    private String code;
    private String name;

    public static String getByCode(String code) {
        for (LockStatusEnum lockStatusEnum : LockStatusEnum.values()) {
            if (lockStatusEnum.getCode().equals(code)) {
                return lockStatusEnum.getName();
            }
        }
        return null;
    }


    SmartLockPwdTypeEnum(String code, String name) {
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
