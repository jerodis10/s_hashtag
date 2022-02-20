package com.s_hashtag.instagram.controller;

import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.repository.InstagramRepository;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class InstagramController {

    private final KakaoApiService kakaoApiService;
    private final InstagramCrawler instagramCrawler;
    private final InstagramRepository instagramRepository;

    @PostMapping("/kakaoMap")
    @ResponseBody
    public List<KakaoPlaceDto> kakaoMap(@RequestParam Map<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));

//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()).setScale(5, RoundingMode.HALF_UP));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()).setScale(5, RoundingMode.HALF_UP));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("ha").toString()).setScale(5, RoundingMode.HALF_UP));
//        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("oa").toString()).setScale(5, RoundingMode.HALF_UP));

        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
        List<KakaoPlaceDto> kakaoPlaceDto = kakaoApiService.findPlaces("FD6", rect, new ArrayList<>());

//        kakaoApiService.savePlaces(param);

        List<Document> list_documonet = new ArrayList<>();
        List<CrawlingDto> list_crawlingDto = new ArrayList<>();
        for(KakaoPlaceDto page : kakaoPlaceDto){
            for(Document document : page.getDocuments()){
                list_documonet.add(document);
                instagramRepository.kakao_document_save(document);

                CrawlingDto crawlingDto = instagramCrawler.crawler(document.getPlaceName());
                list_crawlingDto.add(crawlingDto);
//                instagramRepository.save(crawlingDto);
            }
        }

//        CrawlingDto crawlingDto = instagramCrawler.crawler("당산오돌종로점");

        return kakaoPlaceDto;
    }
}
