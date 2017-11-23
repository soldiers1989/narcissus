package com.ih2home.common.pageUtils;

import java.util.HashMap;

public enum CommYesNoEnum {
	No("否", 0, "FALSE"), Yes("是", 1, "TRUE");

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
	private CommYesNoEnum(String title, int code, String value) {
		this.title = title;
		this.code = code;
		this.value = value;
	}

	public static int getDefaultCode() {
		return No.code;
	}

	public static HashMap<Integer, String> getHash() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		for (CommYesNoEnum s : CommYesNoEnum.values()) {
			hashMap.put(s.getCode(), s.getTitle());
		}
		return hashMap;
	}

	public static String getTitle(int index) {
		for (CommYesNoEnum c : CommYesNoEnum.values()) {
			if (c.getCode() == index) {
				return c.title;
			}
		}
		return null;
	}

	public static CommYesNoEnum getByCode(int code) {
		for (CommYesNoEnum c : CommYesNoEnum.values()) {
			if (c.getCode() == code) {
				return c;
			}
		}
		return null;
	}

	public static void main(String[] argc) {
		for (CommYesNoEnum s : CommYesNoEnum.values()) {
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
