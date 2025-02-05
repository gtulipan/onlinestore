package com.onlineshop.productservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.onlineshop.productservice"})
@EnableR2dbcRepositories(basePackages = "com.onlineshop.productservice.repositories")
public class ProductServiceApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ProductServiceApplication.class, args);
        Environment env = context.getEnvironment();
        log.debug("***************** The {} application started on {} port *****************",
                env.getProperty("spring.application.name"), env.getProperty("server.port"));
    }

//	@PostConstruct
//	public void init() {
//		Dotenv dotenv = Dotenv.configure().load();
//		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
//	}

}
