package com.ih2home.common.pageUtils;

import java.util.HashMap;

/**
 * 适用于 SysUserVO
 * 
 * @author Administrator
 *
 */
public enum QueryConditionLinkEnum {
	AND("和", 1, "and"), OR("或", 2, "or");

	/**
	 * 构造方法
	 * 
	 * @param title
	 *            页面显示字符串
	 * @param code
	 *            数字型值
	 * @param value
	 *            字符型值，如果没有，默认为空
	 */
	private QueryConditionLinkEnum(String title, int code, String value) {
		this.title = title;
		this.code = code;
		this.value = value;
	}

	public static int getDefaultCode() {
		return AND.code;
	}

	public static HashMap<Integer, String> getHash() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		for (QueryConditionLinkEnum s : QueryConditionLinkEnum.values()) {
			hashMap.put(s.getCode(), s.getTitle());
		}
		return hashMap;
	}

	public static String getTitle(int index) {
		for (QueryConditionLinkEnum c : QueryConditionLinkEnum.values()) {
			if (c.getCode() == index) {
				return c.title;
			}
		}
		return null;
	}

	public static void main(String[] argc) {
		for (QueryConditionLinkEnum s : QueryConditionLinkEnum.values()) {
			System.out.println(s.getTitle() + ":" + s.getCode() + ":" + s.getValue() + ":" + s.name());
		}
	}

	private int code;
	private String title;
	private String value;

	public int getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return this.getTitle();
	}

}
