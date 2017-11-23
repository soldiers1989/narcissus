package com.ih2home.utils.common.helper;

import com.ih2home.utils.common.enums.ApiErrorCodeEnum;
import com.ih2ome.comm.api.exception.ApiException;

/**
 * 获取SSO的配置参数
 */
public class EnvHelper {
    public final static String HYPHEN = "___";

    /**
     * 根据业务参数和业务编号获取业务数据
     * TODO: 需要实现
     *
     * @param type
     * @param code
     * @return
     */
    public final static String getEnv(String type, String code) {
        return null;
    }

    /**
     * 验证业务参数对应的业务编号是否正确，如果不正确，根据 apiErrorCodeEnum 抛异常
     * @param type
     * @param code
     * @param apiErrorCodeEnum
     * @throws ApiException
     */
    public final static void checkEnv(String type, String code, ApiErrorCodeEnum apiErrorCodeEnum) throws ApiException {
        String tempValue = EnvHelper.getEnv(type, code);
        if (tempValue == null) {
            throw new ApiException(apiErrorCodeEnum.toApiReturnValueVO(null));
        }
    }
}
