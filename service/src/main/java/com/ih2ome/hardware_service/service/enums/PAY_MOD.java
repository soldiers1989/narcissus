package com.ih2ome.hardware_service.service.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum PAY_MOD {
    PRE_PAYMENT("先付费",1),POST_PAYMENT("后付费",0);

    private String name;
    private int code;
    PAY_MOD(String name,int code) {
        this.name = name;
        this.code = code;
    }

}
