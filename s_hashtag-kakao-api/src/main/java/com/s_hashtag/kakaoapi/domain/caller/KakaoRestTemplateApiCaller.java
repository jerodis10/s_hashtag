package com.s_hashtag.kakaoapi.domain.caller;

import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class KakaoRestTemplateApiCaller {
    private static final String RECT = "rect";
    private static final String PAGE = "page";

    //    private final String URL = "https://dapi.kakao.com/v3/search/book";
    private final String CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category";
    private final String KEYWORD_URL = "https://dapi.kakao.com/v2/local/search/keyword";
    private final String KEY = "af2408226e91805021d1adc7a9d31b36";

    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;

    public KakaoRestTemplateApiCaller(RestTemplate restTemplate, KakaoProperties kakaoProperties) {
        this.restTemplate = restTemplate;
        this.kakaoProperties = kakaoProperties;
    }

    public KakaoPlaceDto findPlaceByCategory(String category, Rect rect, int page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .fromHttpUrl(CATEGORY_URL)
                .queryParam("category_group_code", category)
                .queryParam("rect", rect.toKakaoUriFormat())
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK "+ "af2408226e91805021d1adc7a9d31b36");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("query", "Omens");
//        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class).getBody();
//                return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, Map.class).getBody();
    return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, KakaoPlaceDto.class).getBody();
//        return restTemplate.getForObject(uri.toUriString(), KakaoPlaceDto.class);
    }

    public KakaoPlaceDto findPlaceByKeyword(String category, Rect rect, String query) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .fromHttpUrl(KEYWORD_URL)
                .queryParam("query", query)
                .queryParam("category_group_code", category)
                .queryParam("rect", rect.toKakaoUriFormat())
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK "+ "af2408226e91805021d1adc7a9d31b36");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, KakaoPlaceDto.class).getBody();
    }

    public Boolean isLessOrEqualTotalCount(KakaoPlaceDto kakaoPlaceDto) {
//        int totalCount = kakaoPlaceDto.getTotalCount();
        Integer ret = kakaoPlaceDto.getDocuments().size();
        if(ret <= 14) return true;
//        if(ret >= 1 && ret <= 14) return 1;
        else if(ret >= 15) return false;
        else  return false;
//        return (kakaoProperties.getMaxDocumentCount() * kakaoProperties.getMaxPageableCount()) >= totalCount;
    }
}
