package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(description = "My-Filter", filterName="My-Filter", urlPatterns = "/*", asyncSupported = true)
public class MyFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("filter has been destoried.....");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("enter myFilter doFilter......");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init MyFilter ....");
	}

}
