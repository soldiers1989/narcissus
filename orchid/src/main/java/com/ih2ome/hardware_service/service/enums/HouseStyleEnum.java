package com.ih2ome.hardware_service.service.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/11
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum HouseStyleEnum {
    CONCENTRAT("集中式","0"),DISPERSED("分散式","1");

    private String name;
    private String code;

     HouseStyleEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
