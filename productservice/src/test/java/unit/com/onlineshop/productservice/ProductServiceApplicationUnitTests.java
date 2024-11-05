package com.onlineshop.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ProductServiceApplicationUnitTests {

	@Test
	void contextLoads() {
//		System.out.println(System.getenv("DB_POSTGRESQL_HOST"));
//		System.out.println(System.getenv("DB_POSTGRESQL_PORT"));
//		System.out.println(System.getenv("DB_POSTGRESQL_DATABASE_NAME"));
//		System.out.println(System.getenv("DB_POSTGRESQL_USER"));
//		System.out.println(System.getenv("DB_POSTGRESQL_PASSWORD"));
//		System.out.println(System.getenv("DB_POSTGRESQL_DATABASE_SCHEMA"));
	}
}
