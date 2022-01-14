package com.s_hashtag;

import com.s_hashtag.kakaoapi.domain.caller.AbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SHashtagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagApplication.class, args);
	}

}
