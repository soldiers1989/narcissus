package com.ih2ome.peony.SMSInterface.enums;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/3
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public enum SMSCodeEnum {
    ADD_OR_UPDATE_DOOR_LOCK_PASSWORD(0,"RNQ4X3");
    //［水滴管家]欢迎入住,您的开门密码是@var(password)，有效期自@var(startTime)至@var(endTime)止，在门锁按键输入“密码+#”后，便可开门。感谢您使用果加互联网智能锁
    int code;
    String name;

    SMSCodeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
