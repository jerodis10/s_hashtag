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
public KakaoPlaceDto findPlaceByCategory(String category, Rect rect, int page) {
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
//                .queryParam("x", "126.97954083183751")
//                .queryParam("y", "37.56672525045528")
//                .queryParam("radius", "100")
//                .queryParam("rect", "126.97099029585274,37.56414661025042,126.98802400053437,37.56955554225429")
                .queryParam("rect", rect.toKakaoUriFormat())
//                .queryParam(PAGE, Integer.toString(page))
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

    public Integer isLessOrEqualTotalCount(KakaoPlaceDto kakaoPlaceDto) {
//        int totalCount = kakaoPlaceDto.getTotalCount();
        Integer ret = kakaoPlaceDto.getDocuments().size();
        if(ret >= 1 && ret <= 14) return 1;
        else if(ret >= 15) return 2;
        else  return 0;
//        return ret <= 14 ? ;
//        return (kakaoProperties.getMaxDocumentCount() * kakaoProperties.getMaxPageableCount()) >= totalCount;
    }
}
