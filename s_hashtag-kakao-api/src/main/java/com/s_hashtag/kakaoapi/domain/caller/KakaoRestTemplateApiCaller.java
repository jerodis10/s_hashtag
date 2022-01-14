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
private final String URL = "https://dapi.kakao.com/v2/local/search/category";
    private final String KEY = "af2408226e91805021d1adc7a9d31b36";

    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;

    public KakaoRestTemplateApiCaller(RestTemplate restTemplate, KakaoProperties kakaoProperties) {
        this.restTemplate = restTemplate;
        this.kakaoProperties = kakaoProperties;
    }

//    public KakaoPlaceDto findPlaceByCategory(String category, Rect rect, int page) {
public Map findPlaceByCategory(String category, Rect rect, int page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
//                .path(kakaoProperties.getCategoryUrl())
//                .queryParam(kakaoProperties.getCategoryGroupCode(), category)
//                .path("/v2/local/search/category.json")
//                .path(URL)
                .fromHttpUrl(URL)
//                .queryParam("query", "Omens")
                .queryParam("category_group_code", category)
//                .queryParam("category\\_group\\_code", category)
//                .queryParam(RECT, rect.toKakaoUriFormat())
                .queryParam("radius", 20000)
//                .queryParam(PAGE, Integer.toString(page))
                .build();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK "+ "af2408226e91805021d1adc7a9d31b36");
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
//                .queryParam("query", "Omens");
//        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class).getBody();
                return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, Map.class).getBody();
//        return restTemplate.getForObject(uri.toUriString(), KakaoPlaceDto.class);
    }

    public boolean isLessOrEqualTotalCount(KakaoPlaceDto kakaoPlaceDto) {
        int totalCount = kakaoPlaceDto.getTotalCount();
        return (kakaoProperties.getMaxDocumentCount() * kakaoProperties.getMaxPageableCount()) >= totalCount;
    }
}
