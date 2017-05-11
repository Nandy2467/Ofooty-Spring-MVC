package com.nandeep.ofooty.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

	private static final String PATH = "/error";
	
	/**
	 * Custom error page mapping
	 * @return
	 */
	@RequestMapping(value=PATH)
	public String error(){
		return "thymeleaf/error";
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}

}
