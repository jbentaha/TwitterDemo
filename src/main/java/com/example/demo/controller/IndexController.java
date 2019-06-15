package com.example.demo.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@Autowired
	private HttpSession httpSession;
	
	@GetMapping("/")
	public String twitter() {
		String forward = "login";
		String loged = (String) httpSession.getAttribute("loginSir");
		
		if(loged != null && !loged.equals("")) {
			forward = "twitter";
		}
		
		return forward;
	}
	
	
}