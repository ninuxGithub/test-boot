package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.interceptor.MyInterceptor;

/**
 * 
 * 配置Interceptor
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements EnvironmentAware{
	private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
	@Value("${spring.datasource.url}")
	private String jdbcUrl;
	
	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//自定义资源的映射 可以访问到classpath 目录下的myres 文件夹的内容
		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
		super.addResourceHandlers(registry);
	}

	//test environment
	@Override
	public void setEnvironment(Environment env) {
		logger.info(jdbcUrl);
		
		String java_home = env.getProperty("JAVA_HOME");
		logger.info(java_home);
		
		String url = env.getProperty("spring.datasource.url");
		logger.info("environment get jdbcurl is :"+ url);
		
		propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		String resolever_get_url = propertyResolver.getProperty("url");
		logger.info("get url from property resolver is :"+ resolever_get_url);
	}

}
