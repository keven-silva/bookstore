package com.example.tdd.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class bookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(bookstoreApplication.class, args);
	}
}
