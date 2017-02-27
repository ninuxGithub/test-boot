package com.sample.config.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.sample.config.viewresolver.view.PdfView;

public class PdfViewResolver implements ViewResolver{
	
	private PdfView view;
	
	public PdfViewResolver() {
		super();
		 view = new PdfView();
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return view;
	}

	
}