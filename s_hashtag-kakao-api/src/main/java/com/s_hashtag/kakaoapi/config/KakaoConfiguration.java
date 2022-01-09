package com.s_hashtag.kakaoapi.config;

import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
import com.s_hashtag.kakaoapi.domain.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.domain.caller.KakaoRestTemplateBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
// implements ApplicationRunner
public class KakaoConfiguration {
//    private final KakaoProperties kakaoProperties;
//
//    public KakaoConfiguration(KakaoProperties kakaoProperties) {
//        this.kakaoProperties = kakaoProperties;
//    }
//
//    @Bean
//    public KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller() {
//        RestTemplate restTemplate = KakaoRestTemplateBuilder.get(kakaoProperties)
//                .build();
//        return new KakaoRestTemplateApiCaller(restTemplate, kakaoProperties);
//    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        // 바인딩이 제대로 되었는지 콘솔 출력
//        System.out.println("=================================");
//        System.out.println("kakaoProperties : " + kakaoProperties);
//        System.out.println("getCategoryGroupCode : " + kakaoProperties.getCategoryGroupCode());
//        System.out.println("getKey : " + kakaoProperties.getKey());
//        System.out.println("=================================");
//    }

}
