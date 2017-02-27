package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.example.interceptor.MyInterceptor;
import com.sample.config.viewresolver.JsonViewResolver;
import com.sample.config.viewresolver.PdfViewResolver;
import com.sample.config.viewresolver.XlsViewResolver;

/**
 * 
 * 配置Interceptor
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {
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
		// 自定义资源的映射 可以访问到classpath 目录下的myres 文件夹的内容
		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
		super.addResourceHandlers(registry);
	}

	// test environment
	@Override
	public void setEnvironment(Environment env) {
		logger.info(jdbcUrl);

		String java_home = env.getProperty("JAVA_HOME");
		logger.info(java_home);

		String url = env.getProperty("spring.datasource.url");
		logger.info("environment get jdbcurl is :" + url);

		propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		String resolever_get_url = propertyResolver.getProperty("url");
		logger.info("get url from property resolver is :" + resolever_get_url);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter<?> httpMessageConverter : converters) {
			// 为 MappingJackson2HttpMessageConverter 添加 "application/javascript"
			// 支持，用于响应JSONP的Content-Type
			if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter) httpMessageConverter;
				List<MediaType> medisTypeList = new ArrayList<>(convert.getSupportedMediaTypes());
				medisTypeList.add(MediaType.valueOf("application/javascript;charset=UTF-8"));
				convert.setSupportedMediaTypes(medisTypeList);
				break;
			}
		}
		super.extendMessageConverters(converters);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.TEXT_HTML)
				// .useJaf(true)
				// .favorPathExtension(true)
				.mediaType("xml", MediaType.APPLICATION_XML).mediaType("pdf", MediaType.valueOf("application/pdf"))
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xls", MediaType.valueOf("application/vnd.ms-excel")).ignoreAcceptHeader(true);
	}

	/*
	 * Configure ContentNegotiatingViewResolver
	 */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);

		// Define all possible view resolvers
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		resolvers.add(new JsonViewResolver());
		resolvers.add(new PdfViewResolver());
		resolvers.add(new XlsViewResolver());

		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
		return processor;
	}

}
