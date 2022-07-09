package com.s_hashtag;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableBatchProcessing  // 배치 기능 활성화
//@EnableConfigurationProperties
public class SHashtagWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagWebApplication.class, args);
	}

}
