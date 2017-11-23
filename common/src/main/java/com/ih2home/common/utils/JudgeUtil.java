package com.ih2home.common.utils;

/**
 * <p>
 * Title: 通用工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Alpha Xu
 * @version 1.0 history: Date: Resp Comment 2004-09-09 Alpha Xu Create
 */

import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class JudgeUtil {

	/**
	 * 判断字符串是否是数字形式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(final String str) {
		return str.matches("[-]?[\\d]+[.]?[\\d]*");
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param col
	 * @return
	 */
	public static boolean judgeEmpty(final Object[] col) {
		if ((col == null) || (col.length == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param col
	 * @return
	 */
	public static boolean judgeEmpty(final Collection col) {
		if ((col == null) || (col.isEmpty())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断映射是否为空
	 * 
	 * @param hash
	 * @return
	 */
	public static boolean judgeEmpty(final HashMap hash) {
		if ((hash == null) || (hash.isEmpty())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean judgeEmpty(final String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空,并且不为NULL
	 * 
	 * @param str
	 * @return
	 */
	public static boolean judgeEmptyNULL(final String str) {
		if (!JudgeUtil.judgeEmpty(str) && !(str.equals("NULL"))) {
			return true;
		}
		return false;
	}

}
