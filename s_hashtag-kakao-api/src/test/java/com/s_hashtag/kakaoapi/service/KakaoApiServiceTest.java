package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.rect.Rect;
import com.s_hashtag.kakaoapi.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.rect.location.Latitude;
import com.s_hashtag.kakaoapi.rect.location.Longitude;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class KakaoApiServiceTest {

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
        // data : {ha: 126.75578831035362, oa: 126.7771487382762, pa: 37.41847533960485, qa: 37.44625487247741}, // 10개
        Coordinate minLatitude = new Latitude(new BigDecimal("37.41847533960485"));
        Coordinate maxLatitude = new Latitude(new BigDecimal("37.44625487247741"));
        Coordinate minLongitude = new Longitude(new BigDecimal("126.75578831035362"));
        Coordinate maxLongitude = new Longitude(new BigDecimal("126.7771487382762"));

        //data : {ha: 126.75578831035362, oa: 126.7951487382762, pa: 37.41847533960485, qa: 37.45625487247741}, // 205개
//        Coordinate minLatitude = new Latitude(new BigDecimal("37.41847533960485"));
//        Coordinate maxLatitude = new Latitude(new BigDecimal("37.45625487247741"));
//        Coordinate minLongitude = new Longitude(new BigDecimal("126.75578831035362"));
//        Coordinate maxLongitude = new Longitude(new BigDecimal("126.7951487382762"));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        //when
        List<KakaoPlaceDto> kakaoPlaceList =  kakaoApiService.findPlaces("FD6", rect);

        //then
        log.info("kakaoPlaceList: {}", kakaoPlaceList.size());
//        assertThat(kakaoPlaceList.size()).isEqualTo(1);
    }


    @DisplayName("substring Test")
    @Test
    void substringTest(){
        if("asd".length() < 200) System.out.println("asd");
        else System.out.println("sdf".substring(0,15));
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