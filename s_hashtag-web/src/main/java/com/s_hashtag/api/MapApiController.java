package com.s_hashtag.api;

import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.repository.InstagramRepository;
import com.s_hashtag.instagram.service.InstagramService;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class MapApiController {

    private final KakaoApiService kakaoApiService;
    private final InstagramRepository instagramRepository;
    private final InstagramService instagramService;
//    private final PlatformTransactionManager transactionManager;

    @PostMapping("/kakaoMap")
    @ResponseBody
    public List<KakaoPlaceDto> kakaoMap(@RequestParam Map<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));

        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        instagramService.saveCrawlingResults(rect);

        return new ArrayList<KakaoPlaceDto>();
    }

    @GetMapping("/getHashtag")
    @ResponseBody
    public List<PlaceDto> getHashtag(@RequestParam HashMap<String, Object> param) {
        List<Map<String, Object>> list = new ArrayList<>();
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

//        return instagramRepository.getHashtag(Arrays.asList((String) param.get("category_list[]")), rect);
        return instagramRepository.getHashtag((String) param.get("category_list"), rect);
    }

//    @GetMapping("/admin/members/getHashtag")
//    @ResponseBody
//    public String getHashtag() {
//
//        return "23";
//    }

    @GetMapping("/getHashtagByKeyword")
    @ResponseBody
    public List<PlaceDto> getHashtagByKeyword(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        List<PlaceDto> placeList = new ArrayList<>();

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        for(String category : categoryList) {
            List<KakaoPlaceDto> kakaoPlaceByKeywordList = kakaoApiService.findPlacesByKeyword(category, rect, param.get("searchText").toString(), new ArrayList<>());

            List<String> KeywordStringList = new ArrayList<>();
            for (KakaoPlaceDto kakaoPlaceDto : kakaoPlaceByKeywordList) {
                for (Document document : kakaoPlaceDto.getDocuments()) {
                    KeywordStringList.add(document.getId());
                }
            }

            placeList.addAll(instagramRepository.getHashtagByKeyword(category, KeywordStringList));
        }

        return placeList;
    }

    @GetMapping("/getHashtagByCount")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        List<PlaceDto> placeList = instagramRepository.getHashtagByCount(categoryList, param.get("check").toString());

        return placeList;
    }

    @GetMapping("/getHashtagByCount2")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount2(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        Map<String, Object> hashtag_count_param = new HashMap<>();
        hashtag_count_param.put("check1", param.get("check1"));
        hashtag_count_param.put("check2", param.get("check2"));
        hashtag_count_param.put("check3", param.get("check3"));
        hashtag_count_param.put("check4", param.get("check4"));
        hashtag_count_param.put("check5", param.get("check5"));

        List<PlaceDto> placeList = instagramRepository.getHashtagByCount2(categoryList, hashtag_count_param);

        return placeList;
    }
}
