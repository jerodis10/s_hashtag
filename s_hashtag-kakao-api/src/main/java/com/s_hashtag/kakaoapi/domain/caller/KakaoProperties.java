package com.s_hashtag.kakaoapi.domain.caller;

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
@Component
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String key;
    private String baseUrl;
    private String categoryUrl;
    private String categoryGroupCode;
    private int maxDocumentCount;
    private int maxPageableCount;

//    private final String key;
//    private final String baseUrl;
//    private final String categoryUrl;
//    private final String categoryGroupCode;
//    private final int maxDocumentCount;
//    private final int maxPageableCount;

}
