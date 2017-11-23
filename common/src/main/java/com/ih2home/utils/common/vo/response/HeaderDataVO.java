package com.ih2home.utils.common.vo.response;

import com.ih2home.utils.common.enums.ApiErrorCodeEnum;
import lombok.Data;

@Data
public class HeaderDataVO {
	private String rnd;//随机数
	private String crt;//请求时间
	private String token;//加密串                                                                    
	private ApiErrorCodeEnum rtc;//返回状态s
	private String eds;//错误描述
}
