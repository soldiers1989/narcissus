package com.ih2home.utils.common.enums;



public enum ApiErrorCodeEnum {
    Service_yewu_ok(0, "正常"),
    Service_request_geshi(-1, "请求数据 格式不正确"),
    Service_request_exist(-10, "请求方编号 不存在"),
    Service_request_overtime(-11, "请求方编号 已过期"),
    Service_yewunum_exist(-12, "业务编号 不存在"),
    Service_yewunum_shouquan(-13, "业务编号 未授权"),
    Service_xieyi_exist(-14, "协议版本 不存在"),
    Service_xieyi_shouquan(-15, "协议版本 未授权"),
    Service_xieyi_overtime(-16, "协议版本 已过期"),
    Service_mian_exist(-17, "密案版本 不存在"),
    Service_mian_overtime(-18, "密案版本 已过期"),
    Service_mian_shouquan(-19, "密案版本 未授权"),
    Service_requesttime_geshi(-20, "请求时间 格式不正确"),
    Service_requesttime_overtime(-21, "请求时间 已过期"),
    Service_yewu_exist(-22, "业务单位编号 不存在"),
    Service_yewu_shouquan(-23, "业务单位编号 未授权"),
    Service_interfacenum_exist(-24, "接口编号 不存在"),
    Service_interfacenum_shouquan(-25, "接口编号 未授权"),
    Service_interfacenum_overtime(-26, "接口编号 已过期"),
    Service_interfacever_exist(-27, "接口版本 不存在"),
    Service_interfacever_shouquan(-28, "接口版本 未授"),
    Service_interfacever_overtime(-29, "接口版本 已过期"),
    Service_yewu_fail(-30, "加密串 验证失败");

    private ApiErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public static ApiErrorCodeEnum getByCode(int code) {
        for (ApiErrorCodeEnum c : ApiErrorCodeEnum.values()) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;

    }

}
