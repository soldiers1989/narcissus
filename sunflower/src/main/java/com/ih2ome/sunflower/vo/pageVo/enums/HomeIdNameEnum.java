package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * <br>
 *
 * @author Lucius
 *         create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum HomeIdNameEnum {

   HOME_ID_NAME_JZ("集中式", "jz"),
    HOME_ID_NAME_HM("分散式", "hm") ;



    private String name;
    private String code;

    public static HomeIdNameEnum getByCode(Integer code) {
        for (HomeIdNameEnum alarmTypeEnum : HomeIdNameEnum.values()) {
            if (alarmTypeEnum.getCode().equals(code)) {
                return alarmTypeEnum;
            }
        }
        return null;
    }


    HomeIdNameEnum(String name, String  code) {
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
