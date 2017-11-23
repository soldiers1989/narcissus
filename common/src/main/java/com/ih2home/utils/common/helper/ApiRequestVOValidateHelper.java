package com.ih2home.utils.common.helper;


import com.ih2home.utils.common.ConstUtils;
import com.ih2home.utils.common.exception.ApiException;
import com.ih2home.utils.common.vo.request.ApiRequestVO;
import com.ih2home.utils.common.enums.ApiErrorCodeEnum;

/**
 * API 接口请求数据验证助手
 */
public class ApiRequestVOValidateHelper {
    /**
     * <pre>
     * 验证接口数据
     *
     * 接口访问令牌，用于验证接口参数的完整性，并且防止篡改默认公式为:
     * md5(cid+bid+ver+enc+crt+rnd+pid+pvr+utk+salt+dt)
     * dt格式为请求的JSON
     * </pre>
     *
     * @return
     */
    public static void validateApiRequestVO(ApiRequestVO apiRequestVO) throws ApiException {
        String salt = null;
        //检查 请求方编号
        EnvHelper.checkEnv("cid", apiRequestVO.getHeaderDataVO().getCid(), ApiErrorCodeEnum.Service_request_exist);
        //检查 业务编号
        EnvHelper.checkEnv("bid", apiRequestVO.getHeaderDataVO().getCid() + EnvHelper.HYPHEN + apiRequestVO.getHeaderDataVO().getBid(), ApiErrorCodeEnum.Service_request_exist);
        //检查 协议版本
        EnvHelper.checkEnv("ver", apiRequestVO.getHeaderDataVO().getCid() + EnvHelper.HYPHEN + apiRequestVO.getHeaderDataVO().getBid() + EnvHelper.HYPHEN + apiRequestVO.getHeaderDataVO().getVer(), ApiErrorCodeEnum.Service_request_exist);
        //检查 业务编号
        //TODO:其他验证
        //检查 salt
        salt = EnvHelper.getEnv("salt", apiRequestVO.getHeaderDataVO().getCid());

        //拼串并且验证加密串是否一致
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getCid());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getBid());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getVer());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getEnc());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getCrt());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getRnd());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getPid());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getPvr());
        stringBuilder.append(apiRequestVO.getHeaderDataVO().getUtk());
        stringBuilder.append(salt);
        stringBuilder.append(apiRequestVO.getDataBodyVO().getDt());
        String md5String = ConstUtils.md5(stringBuilder.toString());

        if (!md5String.equals(apiRequestVO.getHeaderDataVO().getTkn())) {
            throw new ApiException(ApiErrorCodeEnum.Service_yewu_fail.toApiReturnValueVO());
        }

        //验证日期
        validateTimestamp(apiRequestVO.getHeaderDataVO().getCrt());


    }

    /**
     * 验证请求时间
     *
     * @param crt
     * @return
     */
    public static void validateTimestamp(String crt) throws ApiException {
        //TODO:需要实现
//        Service_requesttime_geshi(-20, "请求时间 格式不正确"),
//                Service_requesttime_overtime(-21, "请求时间 已过期"),
    }
}
