package com.neu.csye6225.springdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringdemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringdemoApplication.class, args);
	}

}
