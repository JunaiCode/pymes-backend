package com.pdg.pymesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PymesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PymesBackendApplication.class, args);
	}

}
