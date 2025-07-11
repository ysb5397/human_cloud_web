package com.tenco.web;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.web(WebApplicationType.SERVLET).run(args);
	}

}
