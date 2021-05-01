package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterDemoMain {

	public static final String LOGED_USER = "loginUser";
	
	public static void main(String[] args) {
		SpringApplication.run(TwitterDemoMain.class);
	}
	
}
