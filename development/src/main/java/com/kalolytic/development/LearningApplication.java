package com.kalolytic.development;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearningApplication {

	public static void main(String[] args) {

        SpringApplication.run(LearningApplication.class, args);
        System.out.println("hello");
	}

}
