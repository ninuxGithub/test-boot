package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.TestBootApplication;


@RunWith(SpringRunner.class)// SpringJUnit支持，由此引入Spring-Test框架支持！ 
@SpringBootApplication
@SpringApplicationConfiguration(classes=TestBootApplication.class)// 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration// 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class MyTestBootApplicationTests {

	@Test
	public void contextLoads() {
	}

}
