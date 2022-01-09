package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class KakaoApiServiceTest {

    @Autowired
    private KakaoProperties kakaoProperties;

//    KakaoApiServiceTest(KakaoProperties kakaoProperties) {
//        this.kakaoProperties = kakaoProperties;
//    }

    @DisplayName("Property ConfigurationProperty Test")
    @Test
    void configurationPropertyTest() {
        String key = kakaoProperties.getKey();
        assertThat(key).isEqualTo("af2408226e91805021d1adc7a9d31b36");
    }





//    private static final String CATEGORY_GROUP_CODE = "CE7";
//
//    private ObjectMapper objectMapper;
//    private KakaoSecurityProperties kakaoSecurityProperties;
//    private KakaoProperties kakaoProperties;
//    private RestTemplate restTemplate;
//    private KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller;
//    private KakaoApiService kakaoApiService;
//    private MockRestServiceServer server;
//
//    @BeforeEach
//    private void setUp() {
//        this.objectMapper = new ObjectMapper();
//        this.kakaoSecurityProperties = new KakaoSecurityProperties();
//        this.kakaoSecurityProperties.setKey("test_key");
//
//        this.kakaoProperties = new KakaoProperties();
//        this.kakaoProperties.setBaseUrl("https://dapi.kakao.com");
//        this.kakaoProperties.setCategoryUrl("/v2/local/search/category.json");
//        this.kakaoProperties.setCategoryGroupCode("category_group_code");
//        this.kakaoProperties.setMaxDocumentCount(2);
//        this.kakaoProperties.setMaxPageableCount(5);
//
//        this.restTemplate = KakaoRestTemplateBuilder.get(kakaoSecurityProperties, kakaoProperties).build();
//        this.kakaoRestTemplateApiCaller = new KakaoRestTemplateApiCaller(restTemplate, kakaoProperties);
//        this.kakaoApiService = new KakaoApiService(kakaoRestTemplateApiCaller);
//        this.server = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
//    }
}