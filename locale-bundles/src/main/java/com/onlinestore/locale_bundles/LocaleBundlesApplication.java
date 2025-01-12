package com.onlinestore.locale_bundles;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocaleBundlesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocaleBundlesApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
	}
}
