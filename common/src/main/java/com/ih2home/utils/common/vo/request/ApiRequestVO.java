package com.ih2home.utils.common.vo.request;


import com.alibaba.fastjson.JSONObject;
import com.ih2home.utils.common.exception.ApiException;
import com.ih2home.utils.common.helper.ApiRequestVOValidateHelper;
import lombok.Data;

@Data
public class ApiRequestVO {
    HeaderDataVO headerDataVO;
    DataBodyVO dataBodyVO;


    /**
     * 将请求字符串转化为VO
     * @param json
     * @return
     * @throws ApiException
     */
    public static ApiRequestVO fromJson(String json) throws ApiException {
        return JSONObject.parseObject(json,ApiRequestVO.class);
    }

    /**
     * 验证 ApiRequestVO 的数据是否有效
     * @throws ApiException
     */
    public void validate() throws ApiException{
         ApiRequestVOValidateHelper.validateApiRequestVO(this);
    }
}
