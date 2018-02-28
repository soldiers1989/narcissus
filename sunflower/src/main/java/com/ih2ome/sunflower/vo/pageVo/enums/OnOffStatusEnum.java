package com.ih2ome.sunflower.vo.pageVo.enums;

/**
 * <br>
 *
 * @author Lucius
 *         create by 2017/12/7
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum OnOffStatusEnum {

   ON_OFF_STATUS_ENUM_ON_Line("在线", 1),
    ON_OFF_STATUS_ENUM_OFF_Line("离线", 2);



    private String name;
    private Integer code;

    public static OnOffStatusEnum getByCode(Integer code) {
        for (OnOffStatusEnum alarmTypeEnum : OnOffStatusEnum.values()) {
            if (alarmTypeEnum.getCode().equals(code)) {
                return alarmTypeEnum;
            }
        }
        return null;
    }


    OnOffStatusEnum(String name, Integer code) {
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
