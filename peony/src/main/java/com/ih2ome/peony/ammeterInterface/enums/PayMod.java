package com.ih2ome.peony.ammeterInterface.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum PayMod {
    PRE_PAYMENT("prepayment",1),BY_METER("by_meter",0);

    private String name;
    private Integer code;

    PayMod(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public static PayMod getByCode(Integer code){
        if(code.equals(1)){
            return PayMod.PRE_PAYMENT;
        }else if(code.equals(0)){
            return PayMod.BY_METER;
        }else{
            return null;
        }
    }

    public static PayMod getByName(String name){
        if(name.equals("prepayment")){
            return PayMod.PRE_PAYMENT;
        }else if(name.equals("by_meter")){
            return PayMod.BY_METER;
        }else{
            return null;
        }
    }
}
