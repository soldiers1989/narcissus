package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public enum SmartLockFirmEnum {
    GUO_JIA("GJ","com.ih2ome.peony.smartlockInterface.guojia.GuoJiaSmartLock"),
    YUN_DING("YD","com.ih2ome.peony.smartlockInterface.yunding.YunDingSmartLock");
    private String clazz;
    private String code;

    public static SmartLockFirmEnum getByCode(String code) {
        for (SmartLockFirmEnum smartLockFirmEnum: SmartLockFirmEnum.values()) {
            if (smartLockFirmEnum.getCode().equals(code)) {
                return smartLockFirmEnum;
            }
        }
        return null;
    }

    SmartLockFirmEnum(String code,String clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
