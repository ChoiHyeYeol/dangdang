package com.app.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RestTestController {
	
	
	@GetMapping("/test")
	public String Test() {
		return "test";
	}
	


}
