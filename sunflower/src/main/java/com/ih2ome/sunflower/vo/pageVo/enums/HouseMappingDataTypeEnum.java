package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * @author Sky
 * @create 2018/01/24
 * @email sky.li@ixiaoshuidi.com
 **/
public enum HouseMappingDataTypeEnum {
    APARTMENT("1"),
    FLOOR("2"),
    HOUSE("3"),
    ROOM("4"),
    PUBLICZONE("5");
    private String code;

    HouseMappingDataTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
