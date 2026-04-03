package com.activity.activity_logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivityLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityLoggerApplication.class, args);
        System.out.println("Activity Logger Application started successfully!");
	}

}
