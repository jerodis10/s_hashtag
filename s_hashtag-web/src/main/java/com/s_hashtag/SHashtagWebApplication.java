package com.s_hashtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SHashtagWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagWebApplication.class, args);
	}

}
