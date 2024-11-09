package com.example.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskResumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskResumeApplication.class, args);
	}

}
