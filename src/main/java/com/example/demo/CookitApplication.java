package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com.example.demo")
public class CookitApplication {
	
	@RequestMapping("/")
	public String hello() {
		return "Hello world!";
	}

	public static void main(String[] args) {
		SpringApplication.run(CookitApplication.class, args);
	}

}
