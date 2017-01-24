package com.example.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.webjars.WebJarAssetLocator;

/**
 * 前台页面 获取webjar
 */
@Controller
public class WebJarsController {
	private final WebJarAssetLocator assetLocator = new WebJarAssetLocator();

	@RequestMapping("/webjarslocator/{webjar}/**")
	public ResponseEntity<Object> locateWebjarAsset(@PathVariable String webjar, HttpServletRequest request) {
		try {
			String mvcPrefix = "/webjarslocator/" + webjar + "/";
			String mvcPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			String fullPath = assetLocator.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
			return new ResponseEntity<>(new ClassPathResource(fullPath), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
}
