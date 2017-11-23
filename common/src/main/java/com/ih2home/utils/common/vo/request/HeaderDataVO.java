package com.ih2home.utils.common.vo.request;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class HeaderDataVO {
	private String cid;// 请求方编号
	private String bid;// 协议编号
	private String ver;// 协议版本
	private String enc;// 密案版本
	private String crt;// 请求时间
	private String rnd;// 随机数
	private String tkn;// 加密串
	private String pid;// 接口编号
	private String pvr;// 接口版本
	private String utk;// 用户token

	/**
	 * TODO:需要实现
	 * @return
	 */
	public String toJson() {
		return JSONObject.toJSONString(this);
	}
}
