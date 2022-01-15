package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
import com.s_hashtag.kakaoapi.domain.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.RectDivider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class KakaoApiService {
    private static final BigDecimal DEFAULT_OFFSET = BigDecimal.valueOf(0.02);
    private static final BigDecimal HALF = BigDecimal.valueOf(2);
    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;

    private final KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller;

//    @Autowired
//    KakaoProperties kakaoProperties;

    public KakaoApiService(KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller) {
        this.kakaoRestTemplateApiCaller = kakaoRestTemplateApiCaller;
    }
    
    // 속도 개선 필요 -> 파라미터로 리스트 넘기는 방식 말고 다른 방식 고려
    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, List<KakaoPlaceDto> pageList) {
//        List<KakaoPlaceDto> result = new ArrayList<>();

        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 1) {
//        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
            pageList.add(page);
        } else if(kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 2) {
            List<Rect> dividedRects = RectDivider.divide(initialRect);
            for (Rect rect : dividedRects) {
                List<KakaoPlaceDto> nextPages = findPlaces(category, rect, pageList);
            }
        }

        return pageList;
    }


//    public List<KakaoPlaceDto> findPlaces(String category, Rect rect) {
//        return findPlaces(category, rect, DEFAULT_OFFSET);
//    }

//    private List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, BigDecimal offset) {
//        List<Rect> dividedRects = RectDivider.divide(initialRect, offset);
//        List<KakaoPlaceDto> result = new ArrayList<>();

//        for (Rect rect : dividedRects) {
//            KakaoPlaceDto firstPage = kakaoRestTemplateApiCaller.findPlaceByCategory(category, rect, FIRST_PAGE);
//            if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(firstPage)) {
//                result.add(firstPage);
//                result.addAll(findSecondToEndPage(category, rect, firstPage.getPageableCount()));
//                continue;
//            }
//            result.addAll(findPlaces(category, rect, offset.divide(HALF)));
//        }

//        return result;
//    }

//    private List<KakaoPlaceDto> findSecondToEndPage(String category, Rect rect, int pageableCount) {
//        List<KakaoPlaceDto> result = new ArrayList<>();
//
//        for (int pageCount = SECOND_PAGE; pageCount <= pageableCount; pageCount++) {
//            KakaoPlaceDto placeByCategory = kakaoRestTemplateApiCaller.findPlaceByCategory(category, rect, pageCount);
//            result.add(placeByCategory);
//        }
//        return result;
//    }
}
