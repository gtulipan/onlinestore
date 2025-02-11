package com.onlinestore.auth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.onlinestore.auth2"})
@EnableR2dbcRepositories(basePackages = "com.onlinestore.auth2.repositories")
@EnableWebFlux
public class Auth2Application {

    public static void main(String[] args) {
//        try (AnnotationConfigApplicationContext context
//                     = new AnnotationConfigApplicationContext(Auth2Application.class)) {
//            context.getBean(NettyContext.class).onClose().block();
//        }
        ApplicationContext context = SpringApplication.run(Auth2Application.class, args);
        Environment env = context.getEnvironment();
        log.debug("***************** The {} application started on {} port *****************",
                env.getProperty("spring.application.name"), env.getProperty("server.port"));
    }

}
