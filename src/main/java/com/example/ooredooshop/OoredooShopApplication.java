package com.example.ooredooshop;

import com.example.ooredooshop.helpers.RefreshableCRUDRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryBaseClass = RefreshableCRUDRepositoryImpl.class)
@SpringBootApplication
public class OoredooShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OoredooShopApplication.class, args);
	}

}
