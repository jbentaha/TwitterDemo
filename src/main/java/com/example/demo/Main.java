package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.models.LikeeService;
import com.example.demo.models.MessageService;
import com.example.demo.models.UserService;

@SpringBootApplication
public class Main {

	public static MessageService messageService;
	public static UserService userService;
	public static LikeeService likeService;

	public static void main(String[] args) {
		SpringApplication.run(Main.class);
	}
}
