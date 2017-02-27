package com.sample.config.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.sample.config.viewresolver.view.XlsView;

public class XlsViewResolver implements ViewResolver{
	
	private XlsView view;
	
	public XlsViewResolver() {
		super();
		this.view = new XlsView();
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return view;
	}


}