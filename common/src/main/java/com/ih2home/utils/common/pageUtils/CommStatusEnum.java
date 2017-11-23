package com.ih2home.utils.common.pageUtils;

import java.util.HashMap;

/**
 * 适用于 SysUserVO
 * 
 * @author Administrator
 *
 */
public enum CommStatusEnum {
	// 顺序可以自由调整，调整影响在页面的下拉框的显示顺序
	ACTIVE("正常", 1, ""), DELETED("注销", 3, ""), INACTIVE("暂停", 2, ""), PENDING("待审", 0, "");

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
	private CommStatusEnum(String title, int code, String value) {
		this.title = title;
		this.code = code;
		this.value = value;
	}

	public static int getDefaultCode() {
		return PENDING.code;
	}

	public static HashMap<Integer, String> getHash() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
		for (CommStatusEnum s : CommStatusEnum.values()) {
			hashMap.put(s.getCode(), s.getTitle());
		}
		return hashMap;
	}

	public static String getTitle(int index) {
		for (CommStatusEnum c : CommStatusEnum.values()) {
			if (c.getCode() == index) {
				return c.title;
			}
		}
		return null;
	}

	public static void main(String[] argc) {
		for (CommStatusEnum s : CommStatusEnum.values()) {
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
