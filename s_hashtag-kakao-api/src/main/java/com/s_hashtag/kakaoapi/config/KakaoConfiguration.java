package com.s_hashtag.kakaoapi.config;

import com.s_hashtag.kakaoapi.caller.KakaoProperties;
import com.s_hashtag.kakaoapi.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.caller.KakaoRestTemplateBuilder;
import com.s_hashtag.kakaoapi.caller.KakaoSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoConfiguration {

    @Autowired
    KakaoProperties kakaoProperties;

//    private final KakaoProperties kakaoProperties;
//
//    public KakaoConfiguration(KakaoProperties kakaoProperties) {
////        this.kakaoSecurityProperties = kakaoSecurityProperties;
//        this.kakaoProperties = kakaoProperties;
//    }

    @Bean
    public KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller() {
        RestTemplate restTemplate = KakaoRestTemplateBuilder.get(kakaoProperties)
                .build();
        return new KakaoRestTemplateApiCaller(restTemplate, kakaoProperties);
    }
}
