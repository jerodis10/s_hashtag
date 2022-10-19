package com.s_hashtag;

import com.s_hashtag.kakaoapi.caller.KakaoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan
@SpringBootApplication
public class SHashtagWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagWebApplication.class, args);
	}

}




