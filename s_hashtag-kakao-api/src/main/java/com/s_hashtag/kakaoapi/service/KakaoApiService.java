package com.s_hashtag.kakaoapi.service;

import com.s_hashtag.common.kakao.dto.external.Rect;
import com.s_hashtag.kakaoapi.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.rect.RectDivider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KakaoApiService {

    private final KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller;
//    private final InstagramRepository instagramRepository;
//    private final InstagramCrawler instagramCrawler;

    private static final BigDecimal DEFAULT_OFFSET = BigDecimal.valueOf(0.02);
    private static final BigDecimal HALF = BigDecimal.valueOf(2);
    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;

//    @Autowired
//    KakaoProperties kakaoProperties;

//    public KakaoApiService(KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller, InstagramRepository instagramRepository) {
//        this.kakaoRestTemplateApiCaller = kakaoRestTemplateApiCaller;
//        this.instagramRepository = instagramRepository;
//    }

//    public void savePlaces(Map<String, Object> param) {
//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
//        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
//
//        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//        List<KakaoPlaceDto> kakaoPlaceDto = findPlaces("FD6", rect, new ArrayList<>());
//
//        List<Document> list_documonet = new ArrayList<>();
//        List<CrawlingDto> list_crawlingDto = new ArrayList<>();
//        for(KakaoPlaceDto page : kakaoPlaceDto){
//            for(Document document : page.getDocuments()){
//                list_documonet.add(document);
//                instagramRepository.kakao_document_save(document);
//
//                CrawlingDto crawlingDto = instagramCrawler.crawler(document.getPlaceName());
//                list_crawlingDto.add(crawlingDto);
////                instagramRepository.save(crawlingDto);
//            }
//        }
//    }

    List<KakaoPlaceDto> result = new ArrayList<>();

    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect) {
        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
        if(page.getDocuments().size() > 0) {
            if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                result.add(page);
                return result;
            } else if (!kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                List<Rect> dividedRects = RectDivider.divide(initialRect);
                for (Rect rect : dividedRects) {
                    List<KakaoPlaceDto> nextPages = findPlaces(category, rect);
                }
            }
        }

        return result;
    }

    // 속도 개선 필요 -> 파라미터로 리스트 넘기는 방식 말고 다른 방식 고려
//    public List<KakaoPlaceDto> findPlaces(String category, Rect initialRect, List<KakaoPlaceDto> pageList) {
////        List<KakaoPlaceDto> result = new ArrayList<>();
//
//        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByCategory(category, initialRect, FIRST_PAGE);
//        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 1) {
////        if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
//            pageList.add(page);
//        } else if(kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page) == 2) {
//            List<Rect> dividedRects = RectDivider.divide(initialRect);
//            for (Rect rect : dividedRects) {
//                List<KakaoPlaceDto> nextPages = findPlaces(category, rect, pageList);
//            }
//        }
//
//        return pageList;
//    }

    public List<KakaoPlaceDto> findPlacesByKeyword(String category, Rect initialRect, String query, List<KakaoPlaceDto> pageList) {
        KakaoPlaceDto page = kakaoRestTemplateApiCaller.findPlaceByKeyword(category, initialRect, query);
        if(page.getDocuments().size() > 0) {
            if (kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                pageList.add(page);
            } else if (!kakaoRestTemplateApiCaller.isLessOrEqualTotalCount(page)) {
                List<Rect> dividedRects = RectDivider.divide(initialRect);
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
