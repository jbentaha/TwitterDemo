package com.example.demo.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.TwitterDemoMain;

@Controller
public class TwitterDemoLoginController {
	
	@Autowired
	private HttpSession httpSession;
	
	@GetMapping("/login")
	public String login() {
		String forward = "login";
		String loged = (String) httpSession.getAttribute(TwitterDemoMain.LOGED_USER);
		
		if(loged != null) {
			forward = "twitter";
		}
		
		return forward;
	}
}