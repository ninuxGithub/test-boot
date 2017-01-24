package com.example.controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/json")
	public List<String> getJson(){
		String s[] = new String[]{"java","php","delph"};
		return Arrays.asList(s);
	}

}
