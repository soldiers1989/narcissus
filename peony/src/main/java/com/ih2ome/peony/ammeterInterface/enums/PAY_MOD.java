package com.ih2ome.peony.ammeterInterface.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum PAY_MOD {
    PRE_PAYMENT("prepayment",1),BY_METER("by_meter",0);

    private String name;
    private Integer code;
    PAY_MOD(String name,int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public static PAY_MOD getByCode(Integer code){
        if(code.equals(1)){
            return PAY_MOD.PRE_PAYMENT;
        }else if(code.equals(0)){
            return PAY_MOD.BY_METER;
        }else{
            return null;
        }
    }

    public static PAY_MOD getByName(String name){
        if(name.equals("prepayment")){
            return PAY_MOD.PRE_PAYMENT;
        }else if(name.equals("by_meter")){
            return PAY_MOD.BY_METER;
        }else{
            return null;
        }
    }
}
