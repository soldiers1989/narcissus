package com.ih2ome.common.api.vo;


import com.ih2ome.common.api.enums.ApiErrorCodeEnum;
import com.ih2ome.common.vo.ReturnValueVO;

/**
 * Api 错误返回对象
 */
public class ApiReturnValueVO extends ReturnValueVO {
    public ApiReturnValueVO() {
        this.setErrorCode(ReturnValueVO.RESULT_SUCCESS);
    }

    public void setErrorEnum(ApiErrorCodeEnum apiErrorCodeEnum) {
        this.setErrorCode(apiErrorCodeEnum.getCode());
        this.setErrorDescription(apiErrorCodeEnum.getMsg());
    }
}
