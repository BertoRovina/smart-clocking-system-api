package com.humbertorovina.clockingsystem.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication // (exclude = { SecurityAutoConfiguration.class })
@EnableCaching
public class ClockingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClockingSystemApplication.class, args);
	}
}
