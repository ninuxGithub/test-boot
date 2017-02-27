package com.example.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSON;

/**
 * @function:获取IP工具
 * @spring-security-oathority :项目名称
 * @com.hundsun.sso.utils.WebUtils.java 类全路径
 * @2016年12月30日 下午2:29:19
 * @WebUtils
 *
 */
public class WebUtils {
	private static ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

	public static void setIp(String ip) {
		ipThreadLocal.set(ip);
	}

	public static String getIp() {
		return ipThreadLocal.get();
	}

	public static String retriveClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forward-for");
		if (isUnAvaliableIp(ip)) {
			ip = request.getHeader("Proxy-Client-Ip");
		}
		if (isUnAvaliableIp(ip)) {
			ip = request.getHeader("WL-Proxy-Client-Ip");
		}
		if (isUnAvaliableIp(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static boolean isUnAvaliableIp(String ip) {
		return StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
	}



	public static void writeJson(HttpServletResponse response, JSON json) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			json.write(writer);
			writer.flush();
		} catch (IOException e) {
			throw new IllegalStateException("Write json to response error", e);
		}

	}

}
