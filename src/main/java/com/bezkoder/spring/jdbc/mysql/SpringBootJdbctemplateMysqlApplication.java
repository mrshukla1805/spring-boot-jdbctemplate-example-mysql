package com.bezkoder.spring.jdbc.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootJdbctemplateMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbctemplateMysqlApplication.class, args);
	}

}
