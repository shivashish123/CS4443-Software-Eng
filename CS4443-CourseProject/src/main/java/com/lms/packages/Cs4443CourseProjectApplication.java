package com.lms.packages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class Cs4443CourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cs4443CourseProjectApplication.class, args);
	}

}
