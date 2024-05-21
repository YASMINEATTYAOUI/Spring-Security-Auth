package com.example.ooredooshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.example.ooredooshop")
public class OoredooShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OoredooShopApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){ // NEEDED TO ALLOW PASSWORD ENCODER INSIDE SECURITY
		return new BCryptPasswordEncoder();
	}
}
