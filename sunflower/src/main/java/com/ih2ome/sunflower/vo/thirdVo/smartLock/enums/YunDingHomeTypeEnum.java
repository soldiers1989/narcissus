package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

/**
 * @author Sky
 * @create 2018/01/22
 * @email sky.li@ixiaoshuidi.com
 **/
public enum YunDingHomeTypeEnum {
    DISPERSED("分散式公寓","1"),CONCENTRAT("集中式公寓","2");

    private String name;
    private String code;

    YunDingHomeTypeEnum(String name, String code) {
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
