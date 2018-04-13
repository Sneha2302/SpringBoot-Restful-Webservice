package com.users.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.users.controller", "com.users.service"})
public class SampleApplication {
	//Main
	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}
}
