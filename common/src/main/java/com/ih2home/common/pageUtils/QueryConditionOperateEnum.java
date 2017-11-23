package com.ih2home.common.pageUtils;

import java.util.HashMap;

/**
 * 适用于 QueryConditionContentVO
 * 
 * @author Administrator
 *
 */
public enum QueryConditionOperateEnum {
	equal("等于", 1, "="), or("或", 3, "OR"), and("并", 5, "AND"), not("非", 6, "NOT"), GreaterEqual("大于或等于", 7, ">="), Greater("大于", 8, ">"), LessEqual("小于或等于", 9, "<="), Less("小于", 10, "<"), unequal("不等于", 11, "<>"), is("是", 13, "IS"), like("like", 14, "LIKE");

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
	private QueryConditionOperateEnum(String title, int code, String value) {
		this.title = title;
		this.code = code;
		this.value = value;
	}

	public static int getDefaultCode() {
		return equal.code;
	}

	public static HashMap<Integer, String> getHash() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		for (QueryConditionOperateEnum s : QueryConditionOperateEnum.values()) {
			hashMap.put(s.getCode(), s.getTitle());
		}
		return hashMap;
	}

	public static String getTitle(int index) {
		for (QueryConditionOperateEnum c : QueryConditionOperateEnum.values()) {
			if (c.getCode() == index) {
				return c.title;
			}
		}
		return null;
	}

	public static void main(String[] argc) {
		for (QueryConditionOperateEnum s : QueryConditionOperateEnum.values()) {
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
