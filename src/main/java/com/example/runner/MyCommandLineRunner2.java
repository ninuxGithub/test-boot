package com.example.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 2)
public class MyCommandLineRunner2 implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(MyCommandLineRunner2.class);

	@Override
	public void run(String... args) throws Exception {
		logger.info("two ------this runner can load data to in-memery");
	}

}
