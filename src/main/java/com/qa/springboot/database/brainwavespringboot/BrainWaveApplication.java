package com.qa.springboot.database.brainwavespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BrainWaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrainWaveApplication.class, args);
	}
}
