package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class ScheduleConfig {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
	
	
	@Scheduled(cron="0/30 * * * * ?")
	public void mySchedule(){
		logger.info("######################my schedule run...");
	}
	

}
