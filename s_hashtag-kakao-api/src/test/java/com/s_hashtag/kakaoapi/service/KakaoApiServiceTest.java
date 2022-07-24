package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@SpringBootConfiguration
//@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
//@TestPropertySource(locations = "classpath:/application-kakao.yml")
class KakaoApiServiceTest {

//    @Autowired
//    Environment environment;

    @Autowired
    KakaoApiService kakaoApiService;

//    @DisplayName("Property ConfigurationProperty Test")
//    @Test
//    void configurationPropertyTest() {
////        String key = kakaoProperties.getKey();
//        String key = environment.getProperty("key");
//        assertThat(key).isEqualTo("af2408226e91805021d1adc7a9d31b36");
//    }

    @DisplayName("find kakao place Test")
    @Test
    void findKakaoPlace(){
        //given
        Coordinate minLatitude = new Latitude(new BigDecimal("1"));
        Coordinate maxLatitude = new Latitude(new BigDecimal("1"));
        Coordinate minLongitude = new Longitude(new BigDecimal("1"));
        Coordinate maxLongitude = new Longitude(new BigDecimal("1"));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        //when
        List<KakaoPlaceDto> kakaoPlaceList =  kakaoApiService.findPlaces("FD6", rect);

        //then
        assertEquals("주문 취소시 상태는 CANCEL 이다.", "a", "s");
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