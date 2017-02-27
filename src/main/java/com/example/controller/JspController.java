package com.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.repository.Shanhy;

@Controller
public class JspController {

	// ShanhyA 被注册进来了
	@Resource(name = "ShanhyA")
	private Shanhy shanhya;

	// 类成员属性注入方式
	// @Autowired
	// @Qualifier("ds1")
	// @Resource(name = "ds1")
	// private DataSource dataSource1;

	// private DataSource dataSource2;

	@RequestMapping("/success")
	public String testView(Model model) {
		model.addAttribute("name", "spring-mvc-test");
		return "success";
	}

	// org.springframework.boot.autoconfigure.web.BasicErrorController.error(HttpServletRequest)

	// @RequestMapping("/error")
	// public String error(Model model, BindingResult error) {
	// model.addAttribute("error", error.getTarget().toString());
	// return "error";
	// }

	@RequestMapping(value = { "/", "/index" })
	public String index(Map<String, Object> model) {
		System.out.println("shanhya:" + shanhya);
		model.put("time", new Date());
		model.put("message", "test JSONP");
		shanhya.display();

		// System.err.println("dataSource1 :"+dataSource1);
		// System.err.println("dataSource2 :"+dataSource2);
		return "index";
	}

	@RequestMapping("/testJson")
	@ResponseBody
	public Map<String, String> getInfo(@RequestParam(required = false) String name,	@RequestParam(required = false) String name1) {
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("name1", name1);
		return map;
	}
}
