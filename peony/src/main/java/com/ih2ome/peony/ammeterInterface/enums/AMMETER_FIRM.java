package com.ih2ome.peony.ammeterInterface.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum AMMETER_FIRM {
    POWER_BEE("com.ih2ome.peony.ammeterInterface.powerBee.PowerBeeAmmeter");
    private String clazz;

    AMMETER_FIRM(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }
}
