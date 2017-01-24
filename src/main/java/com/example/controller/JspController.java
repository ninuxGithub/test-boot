package com.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.Shanhy;

@Controller
public class JspController {

	//ShanhyA 被注册进来了
	@Resource(name="ShanhyA")
	private Shanhy shanhya;
	
	// 类成员属性注入方式
//	@Autowired
//	@Qualifier("ds1")
//	@Resource(name = "ds1")
//	private DataSource dataSource1;

//	private DataSource dataSource2;

	@RequestMapping("/success")
	public String testView(Model model) {
		model.addAttribute("name", "spring-mvc-test");
		return "success";
	}

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		System.out.println("shanhya:"+ shanhya);
		shanhya.display();
		
//		System.err.println("dataSource1 :"+dataSource1);
//		System.err.println("dataSource2 :"+dataSource2);
		return "index";
	}
}
