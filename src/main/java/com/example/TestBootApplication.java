package com.example;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.datasource.DynamicDataSourceRegister;
import com.example.servlet.MyServlet;


/**
 * 
 *@ServletComponentScan 对于Servlet 组件进行了扫描 --针对的是采用注解的Servlet
 *@Bean 对配置的Servlet 进行配置
 */

@EnableAsync //支持Servlet异步
@SpringBootApplication
@ServletComponentScan
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class TestBootApplication extends SpringBootServletInitializer{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestBootApplication.class);

	public static void main(String[] args) {
		logger.info("enter main");
		SpringApplication.run(TestBootApplication.class, args);
	}
	
	@Bean(name="test")
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new MyServlet(), "/my-servlet/*");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestBootApplication.class);
	}
	
	
}
