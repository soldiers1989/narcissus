package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

/**
 * @author Sky
 * @create 2018/01/23
 * @email sky.li@ixiaoshuidi.com
 **/
public enum YunDingPullHomeCountEnum {
    ONE_HUNDRED("100"),
    FIVE_HUNDRED("500"),
    ONE_THOUSAND("1000");
    private String count;

    YunDingPullHomeCountEnum(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
