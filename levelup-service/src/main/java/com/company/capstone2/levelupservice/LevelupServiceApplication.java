package com.company.capstone2.levelupservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LevelupServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelupServiceApplication.class, args);
	}

}
