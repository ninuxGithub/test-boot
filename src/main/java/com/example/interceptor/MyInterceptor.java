package com.example.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.utils.WebUtils;

public class MyInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		System.out.println("after completion 渲染试图到jsp 之后执行  （资源的清理）");

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {
		System.out.println("结果controller 在结果了controller之后在执行 ，在渲染之前");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String ip = WebUtils.retriveClientIp(request);
		System.out.println("pre Handle : 在结果controller之前执行   *** user ip :"+ip);
		return true;
	}

}
