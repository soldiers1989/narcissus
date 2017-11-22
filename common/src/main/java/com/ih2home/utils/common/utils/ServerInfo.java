package com.ih2home.utils.common.utils;

import javax.servlet.http.HttpServletRequest;

public class ServerInfo {
	public static boolean isAjax(HttpServletRequest request) {
		if (request != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")))
			return true;
		return false;
	}

}
