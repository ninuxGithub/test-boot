package com.sample.config.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sample.config.viewresolver.view.MMappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver{
	
	private MappingJackson2JsonView view;

	public JsonViewResolver() {
		super();
		view = new MMappingJackson2JsonView();
        view.setPrettyPrint(true);
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
	}

}
