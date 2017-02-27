package com.example;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.example.datasource.DynamicDataSourceRegister;
import com.example.servlet.MyServlet;

/**
 * 
 * @ServletComponentScan 对于Servlet 组件进行了扫描 --针对的是采用注解的Servlet
 * @Bean 对配置的Servlet 进行配置
 * 
 *       spring Boot 使用事务非常简单，首先使用注解 @EnableTransactionManagement 开启事务支持后，
 *       然后在访问数据库的Service方法上添加注解 @Transactional 便可。
 */

@EnableAsync // 支持Servlet异步
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@Import({ DynamicDataSourceRegister.class }) // 注册动态多数据源
public class TestBootApplication extends SpringBootServletInitializer implements TransactionManagementConfigurer {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestBootApplication.class);

	// @Value("${server.port}")
	// private String serverPort;
	//
	// @Autowired
	// private DevSend message;
	//
	// public void init() {
	// message.send();
	// System.out.println("dev server port is :" + serverPort);
	// }

	@Resource(name = "txManager2")
	private PlatformTransactionManager txManager2;

	public static void main(String[] args) {
		logger.info("enter main");
		SpringApplication.run(TestBootApplication.class, args);
	}

	@Bean(name = "test")
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new MyServlet(), "/my-servlet/*");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestBootApplication.class);
	}

	// 如果你添加的是 spring-boot-starter-data-jpa 依赖，框架会默认注入 JpaTransactionManager 实例
	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		// >>>>>>>>>>org.springframework.orm.jpa.JpaTransactionManager
		return new Object();
	}
	
	
//	如果Spring容器中存在多个 PlatformTransactionManager 实例，并且没有实现接口 TransactionManagementConfigurer 指定默认值，
//	在我们在方法上使用注解 @Transactional 的时候，就必须要用value指定，如果不指定，则会抛出异常。
//	对于系统需要提供默认事务管理的情况下，实现接口 TransactionManagementConfigurer 指定。
//	对有的系统，为了避免不必要的问题，在业务中必须要明确指定 @Transactional 的 value 值的情况下。
//	不建议实现接口 TransactionManagementConfigurer，这样控制台会明确抛出异常，开发人员就不会忘记主动指定
	// 实现了TransactionManagementConfigurer 接口
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager2;
	}

	// 创建事务管理器1
	@Primary
	@Bean(name = "txManager1")
	public PlatformTransactionManager txManager(DataSource dataSource) {
		logger.info("注入txManager1 优先使用--DataSourceTransactionManager");
		return new DataSourceTransactionManager(dataSource);
	}

	// 创建事务管理器2
	@Bean(name = "txManager2")
	public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
		logger.info("注入txManager2 ----JpaTransactionManager ");
		return new JpaTransactionManager(factory);
	}

}
