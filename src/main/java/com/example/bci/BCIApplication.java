package com.example.bci;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaRepositories
public class BCIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BCIApplication.class, args);
	}

}
