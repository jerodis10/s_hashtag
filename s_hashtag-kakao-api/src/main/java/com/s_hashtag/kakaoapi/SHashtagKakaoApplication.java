package com.s_hashtag.kakaoapi;

import com.s_hashtag.kakaoapi.caller.KakaoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties({KakaoProperties.class})
@ConfigurationPropertiesScan
@SpringBootApplication
public class SHashtagKakaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SHashtagKakaoApplication.class, args);
	}

}
