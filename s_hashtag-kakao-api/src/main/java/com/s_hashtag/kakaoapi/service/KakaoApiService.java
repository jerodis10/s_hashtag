package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.kakaoapi.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.factory.RectFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KakaoApiService {

    private final KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller;

    private static final int FIRST_PAGE = 1;

    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, List<KakaoPlaceDto> pageList) {
        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
            pageList.add(page);
        } else if(!kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
            List<Rect> dividedRects = RectFactory.divide(initialRect);
            for (Rect rect : dividedRects) {
                findPlaces(category, rect, pageList);
            }
        }

        return pageList;
    }

    public List<KakaoPlaceDto> findPlacesByKeyword(String category, Rect initialRect, String query, List<KakaoPlaceDto> pageList) {
        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByKeyword(category, initialRect, query);
        if(page.getDocuments().size() > 0) {
            if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                pageList.add(page);
            } else if (!kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                List<Rect> dividedRects = RectFactory.divide(initialRect);
                for (Rect rect : dividedRects) {
                    findPlacesByKeyword(category, rect, query, pageList);
                }
            }
        }

        return pageList;
    }
}
