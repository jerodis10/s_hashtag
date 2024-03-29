package com.s_hashtag.instagram.service;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.common.domain.kakao.repository.KakaoRepository;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstagramService {

    private final KakaoApiService kakaoApiService;
    private final InstagramSavePlace instagramSavePlace;

    @Transactional
    public void saveCrawlingResults(Rect rect) {
        savePlace(rect, "FD6");
//        savePlace(rect, "CE7");

    }

    @Transactional
    public void savePlace(Rect rect, String category) {
        try {
            List<KakaoPlaceDto> kakaoPlaceDto = kakaoApiService.findPlaces(category, rect, new ArrayList<>());
            int count = 0;
            for (KakaoPlaceDto page : kakaoPlaceDto) {
                for (Document document : page.getDocuments()) {
                    instagramSavePlace.savePlaceByProcess(document);
                    log.info("savePlaceByProcess Count : {}", ++count);
                }
            }
        } catch (CrawlerException crawlerException) {
            log.debug("CrawlerException: {}", crawlerException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
