package com.s_hashtag.kakaoapi.caller;

import com.s_hashtag.kakaoapi.exception.KakaoExceptionHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;

public class KakaoRestTemplateBuilder {
    public static RestTemplateBuilder get(KakaoProperties kakaoProperties) {
        return new RestTemplateBuilder()
            .rootUri(kakaoProperties.getBaseUrl())
            .errorHandler(new KakaoExceptionHandler())
            .defaultHeader("Authorization", "KakaoAK " + kakaoProperties.getKey())
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(10));
    }
}
