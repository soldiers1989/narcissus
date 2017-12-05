package com.ih2ome.common.api.vo.response;


import lombok.Data;

@Data
public class HeaderResponseDataVO {
	private String rnd;//随机数
	private String crt;//请求时间
	private String token;//加密串                                                                    
	private String rtc;//返回状态s
	private String eds;//错误描述
}
