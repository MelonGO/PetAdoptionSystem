package com.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import tools.RunRedis;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { return builder.sources(DemoApplication.class);
	}


	public static void main(String[] args) {
		RunRedis r=new RunRedis();
		SpringApplication.run(DemoApplication.class, args);
	}
}
