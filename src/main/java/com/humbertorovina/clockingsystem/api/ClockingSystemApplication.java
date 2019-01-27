package com.humbertorovina.clockingsystem.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class ClockingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClockingSystemApplication.class, args);
	}

}

