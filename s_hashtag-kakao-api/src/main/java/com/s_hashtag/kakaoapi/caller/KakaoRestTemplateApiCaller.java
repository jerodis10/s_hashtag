package com.s_hashtag.kakaoapi.caller;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
//@RequiredArgsConstructor
public class KakaoRestTemplateApiCaller {

    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;

    public KakaoRestTemplateApiCaller(RestTemplate restTemplate, KakaoProperties kakaoProperties) {
        this.restTemplate = restTemplate;
        this.kakaoProperties = kakaoProperties;
    }

//    private final String CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category";
//    private final String KEYWORD_URL = "https://dapi.kakao.com/v2/local/search/keyword";
//    private final String KEY = "af2408226e91805021d1adc7a9d31b36";

//
//    public KakaoRestTemplateApiCaller(RestTemplate restTemplate, KakaoProperties kakaoProperties) {
//        this.restTemplate = restTemplate;
//        this.kakaoProperties = kakaoProperties;
//    }

    public KakaoPlaceDto findPlaceByCategory(String category, Rect rect, int page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .fromHttpUrl(kakaoProperties.getBaseUrl() + kakaoProperties.getCategoryUrl())
                .queryParam(kakaoProperties.getCategoryGroupCode(), category)
                .queryParam(kakaoProperties.getRect(), rect.toKakaoUriFormat())
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK "+ kakaoProperties.getKey());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, KakaoPlaceDto.class).getBody();
    }

    public KakaoPlaceDto findPlaceByKeyword(String category, Rect rect, String query) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .fromHttpUrl(kakaoProperties.getBaseUrl() + kakaoProperties.getKeywordUrl())
                .queryParam(kakaoProperties.getQuery(), query)
                .queryParam(kakaoProperties.getCategoryGroupCode(), category)
                .queryParam(kakaoProperties.getRect(), rect.toKakaoUriFormat())
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + kakaoProperties.getKey());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, KakaoPlaceDto.class).getBody();
    }

    public Boolean isLessOrEqualTotalCount(KakaoPlaceDto kakaoPlaceDto) {
//        int totalCount = kakaoPlaceDto.getTotalCount();
        int totalCount = kakaoPlaceDto.getDocuments().size();

        if(totalCount < kakaoProperties.getMaxDocumentCount()) return true;
        else if(totalCount >= kakaoProperties.getMaxDocumentCount()) return false;
        return false;
//        return (kakaoProperties.getMaxDocumentCount() * kakaoProperties.getMaxPageableCount()) >= totalCount;
    }
}
