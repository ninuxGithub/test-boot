package com.example.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

@WebServlet(urlPatterns = "/druid/*", initParams = {
		@WebInitParam(name = "allow", value = "10.1.5.96,127.0.0.1"), // 白名单
		@WebInitParam(name = "deny", value = "10.1.5.100"), // 黑名单
		@WebInitParam(name = "loginUsername", value = "tom"), // 登录名称
		@WebInitParam(name = "loginPassword", value = "java"), // 登录密码
		@WebInitParam(name = "resetEnable", value = "false")// 禁止rest all
})
public class DruidStateViewServlet extends StatViewServlet {

	private static final long serialVersionUID = -121684093660865473L;

}
