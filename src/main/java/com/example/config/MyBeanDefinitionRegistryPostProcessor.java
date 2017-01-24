package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;

@Configuration
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(MyBeanDefinitionRegistryPostProcessor.class);

	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		logger.info(">>>>postProcessBeanFactory invoke ..");

		// BeanDefinition bdf = factory.getBeanDefinition("dataSourceA");
		// MutablePropertyValues mv = bdf.getPropertyValues();
		// mv.addPropertyValue("driverClassName", "com.mysql.jdbc.Driver");
		// mv.addPropertyValue("url", "jdbc:mysql://localhost:3306/test");
		// mv.addPropertyValue("username", "root");
		// mv.addPropertyValue("password", "root");

	}

	// 注册一个dadaSourceA Bean

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registory) throws BeansException {
		// registerBean(registory, "dataSourceA",
		// org.apache.tomcat.jdbc.pool.DataSource.class);
		registerBean(registory, "ShanhyA", ShanhyA.class);
	}

	private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass) {
		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
		abd.setScope(scopeMetadata.getScopeName());
		// 可以自动生成name
		String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, registry));

		AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
	}

}
