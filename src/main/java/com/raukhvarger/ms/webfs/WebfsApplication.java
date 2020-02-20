package com.raukhvarger.ms.webfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class WebfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfsApplication.class, args);
	}

}
