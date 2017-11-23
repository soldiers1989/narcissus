package com.ih2home.common.api.exception;


import com.ih2home.common.api.vo.ApiReturnValueVO;

/**
 * Web Api 接口调用异常类
 */
public class ApiException extends Exception {
    ApiReturnValueVO apiReturnValueVO;

    public ApiException(ApiReturnValueVO apiReturnValueVO) {
        this.apiReturnValueVO = apiReturnValueVO;
    }

    public ApiReturnValueVO getApiReturnValueVO() {
        return apiReturnValueVO;
    }
}
