package com.example.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

	private static final String DEFAULT_DEFINIT_DATASOURCE_NAME = "dataSource";
	
	private static final String DEFAULT_TARGET_DATASOURCE = "defaultTargetDataSource";
	
	private static final String TARGET_DATASOURCES = "targetDataSources";
	
	private static final Object DEFAULT_DATASOURCE_TYPE = "org.apache.tomcat.jdbc.pool.DataSource";
	
	private DataSource defaultDataSource;

	private PropertyValues dataSourcePropertyValues;

	private ConversionService conversionService = new DefaultConversionService();


	private Map<String, DataSource> customDataSources = new HashMap<>();

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metaDate, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		// 将主数据源添加到目标数据源集合中去
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		
		// 添加其他自定义的数据源
		targetDataSources.putAll(customDataSources);
		for (String key : customDataSources.keySet()) {
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
		}
		
		// 创建DynamicDataSource
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		beanDefinition.setSynthetic(true);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", beanDefinition);
		registry.registerBeanDefinition("dataSource", beanDefinition);
		logger.info("registry dynamic data soruce....");
		
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		System.err.println("setEnvironment.. ");
		initDefaultDataSource(environment);
		initCustomDataSources(environment);
	}

	/**
	 * 自定义数据源
	 * 
	 * @param environment
	 */
	private void initCustomDataSources(Environment environment) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "custom.datasource.");
		String datasourcePrefixs = propertyResolver.getProperty("names");
		for (String prefix : datasourcePrefixs.split(",")) {
			Map<String, Object> map = propertyResolver.getSubProperties(prefix + ".");
			DataSource ds = buildDataSource(map);
			customDataSources.put(prefix, ds);
			dataBinder(ds, environment);
		}
	}

	/**
	 * 默认配置的数据源
	 * 
	 * @param environment
	 */
	private void initDefaultDataSource(Environment environment) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
		Map<String, Object> datasourcePropertyMap = new HashMap<>();
		datasourcePropertyMap.put("type", propertyResolver.getProperty("type"));
		datasourcePropertyMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
		datasourcePropertyMap.put("url", propertyResolver.getProperty("url"));
		datasourcePropertyMap.put("username", propertyResolver.getProperty("username"));
		datasourcePropertyMap.put("password", propertyResolver.getProperty("password"));

		defaultDataSource = buildDataSource(datasourcePropertyMap);
		dataBinder(defaultDataSource, environment);

	}

	private void dataBinder(DataSource defaultDataSource, Environment environment) {
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(defaultDataSource);
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);
		dataBinder.setIgnoreInvalidFields(false);
		dataBinder.setIgnoreUnknownFields(true);

		if (dataSourcePropertyValues == null) {
			Map<String, Object> rpr = new RelaxedPropertyResolver(environment, "spring.datasource")
					.getSubProperties(".");
			Map<String, Object> values = new HashMap<>(rpr);
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}
		dataBinder.bind(dataSourcePropertyValues);
	}

	@SuppressWarnings("unchecked")
	private DataSource buildDataSource(Map<String, Object> datasourcePropertyMap) {
		try {
			Object type = datasourcePropertyMap.get("type");
			if (type == null) {
				type = DEFAULT_DATASOURCE_TYPE;
			}
			Class<? extends DataSource> dataSourceType;
			dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

			String driverClassName = datasourcePropertyMap.get("driver-class-name").toString();
			String url = datasourcePropertyMap.get("url").toString();
			String username = datasourcePropertyMap.get("username").toString();
			String password = datasourcePropertyMap.get("password").toString();
			DataSourceBuilder builder = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
					.username(username).password(password).type(dataSourceType);
			return builder.build();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}
