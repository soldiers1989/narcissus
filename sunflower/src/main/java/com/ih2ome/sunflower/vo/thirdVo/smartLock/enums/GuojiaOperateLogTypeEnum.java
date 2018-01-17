package com.ih2ome.sunflower.vo.thirdVo.smartLock.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/4
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum  GuojiaOperateLogTypeEnum {
    CREATE("create","创建操作"),DELETE("delete","删除操作"),MODIFY("modify","修改操作"),ENABLE("enable","允许操作"),DISABLE("disable","禁止操作");
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
