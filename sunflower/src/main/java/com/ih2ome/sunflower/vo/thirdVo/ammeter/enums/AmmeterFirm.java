package com.ih2ome.sunflower.vo.thirdVo.ammeter.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum AmmeterFirm {
    POWER_BEE("com.ih2ome.peony.ammeterInterface.powerBee.PowerBeeAmmeter");
    private String clazz;

    AmmeterFirm(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }
}
