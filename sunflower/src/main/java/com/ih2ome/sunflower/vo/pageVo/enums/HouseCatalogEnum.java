package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * <br>
 *
 * @author Lucius
 *         create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum HouseCatalogEnum {

   HOUSE_CATALOG_ENUM_CASPAIN("分散式", 1),
    HOUSE_CATALOG_ENUM_VOLGA("集中式",2);


    private String name;
    private Integer code;

    public static HouseCatalogEnum getByCode(Integer code) {
        for (HouseCatalogEnum alarmTypeEnum : HouseCatalogEnum.values()) {
            if (alarmTypeEnum.getCode().equals(code)) {
                return alarmTypeEnum;
            }
        }
        return null;
    }


    HouseCatalogEnum(String name, Integer code) {
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
