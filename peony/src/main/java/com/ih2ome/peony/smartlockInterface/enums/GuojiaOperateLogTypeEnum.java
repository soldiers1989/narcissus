package com.ih2ome.peony.smartlockInterface.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/4
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum  GuojiaOperateLogTypeEnum {
    CREATE("create","创建"),DELETE("delete","删除"),MODIFY("modify","修改"),ENABLE("enable","允许"),DISABLE("disable","禁止");
    String code;
    String operateName;

    GuojiaOperateLogTypeEnum(String code, String operateName) {
        this.code = code;
        this.operateName = operateName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public static JSONObject enum2Json(){
        JSONObject jsonObject = new JSONObject();
        for (GuojiaOperateLogTypeEnum guojiaOperateLogTypeEnum : GuojiaOperateLogTypeEnum.values()) {
            jsonObject.put(guojiaOperateLogTypeEnum.getCode(),guojiaOperateLogTypeEnum.getOperateName());
        }
        return jsonObject;
    }
}
