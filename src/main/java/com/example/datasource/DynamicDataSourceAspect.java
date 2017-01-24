package com.example.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(-1) //保证在@Transactional 之前
@Aspect
@Component
public class DynamicDataSourceAspect {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

	@Before("@annotation(ds)")
	public void changeDataSource(JoinPoint point, TargetDataSource ds) {
		String dsid = ds.name();
		if (!DynamicDataSourceContextHolder.containsDataSource(dsid)) {
			logger.info("数据源[{}]不存在， 使用默认数据源 >{}", ds.name(), point.getSignature());
		} else {
			logger.info("User DataSource :{} > {} ", ds.name(), point.getSignature());
			DynamicDataSourceContextHolder.setDataSourceType(ds.name());
		}
	}

	@After("@annotation(ds)")
	public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
		logger.debug("Revert DataSource :{} > {} ", ds.name(), point.getSignature());
		DynamicDataSourceContextHolder.clearDataSourceType();
	}
}
