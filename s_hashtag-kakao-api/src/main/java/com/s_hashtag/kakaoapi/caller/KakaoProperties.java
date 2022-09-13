package com.s_hashtag.kakaoapi.caller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

//@Getter
//@RequiredArgsConstructor
//@ConstructorBinding
@Getter
@Setter
@ConfigurationProperties(prefix = "kakao")
@Component
public class KakaoProperties {
    private String key;
    private String baseUrl;
    private String categoryUrl;
    private String keywordUrl;
    private String categoryGroupCode;
    private String rect;
    private String query;
    private int maxDocumentCount;
    private int maxPageableCount;

}
