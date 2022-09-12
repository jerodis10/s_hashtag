package com.s_hashtag.kakaoapi.caller;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
public class KakaoRestTemplateApiCaller {
    private static final String RECT = "rect";
    private static final String PAGE = "page";

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
        try {
            UriComponents uri = UriComponentsBuilder.newInstance()
                    .fromHttpUrl(KEYWORD_URL)
                    .queryParam("query", query)
                    .queryParam("category_group_code", category)
                    .queryParam("rect", rect.toKakaoUriFormat())
                    .build();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "KakaoAK " + "af2408226e91805021d1adc7a9d31b36");
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            return restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, KakaoPlaceDto.class).getBody();
        } catch (HttpStatusException e) {
            log.info("url = {}", url);
            if (e.getStatusCode() == NOT_FOUND) {
//                throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_URL);
                return null;
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
            }
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } catch (IOException e) {
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        }
    }

    public Boolean isLessOrEqualTotalCount(KakaoPlaceDto kakaoPlaceDto) {
//        int totalCount = kakaoPlaceDto.getTotalCount();
        int totalCount = kakaoPlaceDto.getDocuments().size();
        if(totalCount <= 14) return true;
        else if(totalCount >= 15) return false;
        else  return false;
//        return (kakaoProperties.getMaxDocumentCount() * kakaoProperties.getMaxPageableCount()) >= totalCount;
    }
}
