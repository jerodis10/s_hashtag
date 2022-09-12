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
//    private static final BigDecimal DEFAULT_OFFSET = BigDecimal.valueOf(0.02);
//    private static final BigDecimal HALF = BigDecimal.valueOf(2);
//    private static final int SECOND_PAGE = 2;

//    @Autowired
//    KakaoProperties kakaoProperties;


    // 속도 개선 필요 -> 파라미터로 리스트 넘기는 방식 말고 다른 방식 고려
    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, List<KakaoPlaceDto> pageList) {
        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
            pageList.add(page);
        } else if(!kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
//            List<Rect> dividedRects = RectDivider.divide(initialRect);
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
//                List<Rect> dividedRects = RectDivider.divide(initialRect);
                List<Rect> dividedRects = RectFactory.divide(initialRect);
                for (Rect rect : dividedRects) {
                    List<KakaoPlaceDto> nextPages = findPlacesByKeyword(category, rect, query, pageList);
                }
            }
        }

        return pageList;
    }

//    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, List<KakaoPlaceDto> pageList) {
////        List<KakaoPlaceDto> result = new ArrayList<>();
//
//        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
////        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 1) {
//////        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
////            pageList.add(page);
////        } else if(kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 2) {
////            List<Rect> dividedRects = RectDivider.divide(initialRect);
////            for (Rect rect : dividedRects) {
////                List<KakaoPlaceDto> nextPages = findPlaces(category, rect, pageList);
////            }
////        }
//        pageList.add(page);
//        return pageList;
//    }


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
