package com.s_hashtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableBatchProcessing  // 배치 기능 활성화

//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
// If your application does not require the spring boot batch tables, the following configurations
// should be added to prevent the tables from being created

//@EnableConfigurationProperties
public class SHashtagWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagWebApplication.class, args);
	}

}


