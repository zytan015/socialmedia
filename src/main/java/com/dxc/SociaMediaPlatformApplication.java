package com.dxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
@CrossOrigin(origins = "*", maxAge=3600)
public class SociaMediaPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SociaMediaPlatformApplication.class, args);
	}

}