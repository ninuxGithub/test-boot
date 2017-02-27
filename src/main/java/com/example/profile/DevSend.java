package com.example.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("dev")
public class DevSend implements Message {

//	在启动程序的时候通过添加 –spring.profiles.active={profile} 来指定具体使用的配置 
//	例如我们执行 java -jar demo.jar –spring.profiles.active=dev 那么上面3个文件中的内容将被如何应用？ 
//	Spring Boot 会先加载默认的配置文件，然后使用具体指定的profile中的配置去覆盖默认配置。
//
//	app.name 只存在于默认配置文件 application.properties 中，因为指定环境中不存在同样的配置，所以该值不会被覆盖 
//	server.port 默认为8080，但是我们指定了环境后，将会被覆盖。如果指定stg环境，server.port 则为 8082 
//	spring.profiles.active 默认指定dev环境，如果我们在运行时指定 –spring.profiles.active=stg 那么将应用stg环境，最终 server.port 的值为8082
	
	
	@Override
	public void send() {
		System.err.println("this is dev send method");
	}

}
