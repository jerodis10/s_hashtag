package com.s_hashtag.api;

import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.instagram.service.InstagramService;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.dto.presentation.KakaoMapDto;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class MapApiController {

    private final KakaoApiService kakaoApiService;
    private final InstagramRepository instagramRepository;
    private final InstagramService instagramService;
//    private final PlatformTransactionManager transactionManager;

//    @PostMapping("/kakaoMap")
//    @ResponseBody
//    public List<KakaoPlaceDto> kakaoMap(@RequestParam Map<String, Object> param) {
//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
//        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
//
//        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//        instagramService.saveCrawlingResults(rect);
//
//        return new ArrayList<KakaoPlaceDto>();
//    }

    @PostMapping("/kakaoMap")
    @ResponseBody
    public void kakaoMap(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        if (errors.hasErrors()){
            log.error("KakaoMapDto 바인딩 에러 : ", errors.getAllErrors());
        }

        instagramService.saveCrawlingResults(kakaoMapDto.CreateRect());
    }

    @PostMapping("/getHashtag")
    @ResponseBody
    public List<PlaceDto> getHashtag(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        if (errors.hasErrors()){
            log.error("KakaoMapDto 바인딩 에러 : ", errors.getAllErrors());
        }

        return instagramRepository.findAll(kakaoMapDto.getCategory(), kakaoMapDto.CreateRect());
    }

//    @GetMapping("/getHashtagByKeyword")
//    @ResponseBody
//    public List<PlaceDto> getHashtagByKeyword(@RequestParam HashMap<String, Object> param) {
//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
//        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
//        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//        List<PlaceDto> placeList = new ArrayList<>();
//
//        String temp = (String) param.get("category_list");
//        String[] categoryList = temp.split(",");
//
//        for(String category : categoryList) {
//            List<KakaoPlaceDto> kakaoPlaceByKeywordList = kakaoApiService.findPlacesByKeyword(category, rect, param.get("searchText").toString(), new ArrayList<>());
//
//            List<String> KeywordStringList = new ArrayList<>();
//            for (KakaoPlaceDto kakaoPlaceDto : kakaoPlaceByKeywordList) {
//                for (Document document : kakaoPlaceDto.getDocuments()) {
//                    KeywordStringList.add(document.getId());
//                }
//            }
//
//            placeList.addAll(instagramRepository.getHashtagByKeyword(category, KeywordStringList));
//        }
//
//        return placeList;
//    }

    @PostMapping("/getHashtagByKeyword")
    @ResponseBody
    public List<PlaceDto> getHashtagByKeyword(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        List<PlaceDto> placeList = new ArrayList<>();
        String temp = kakaoMapDto.getCategory();
        String[] categoryList = temp.split(",");

        for(String category : categoryList) {
            List<String> KeywordStringList = new ArrayList<>();

            List<KakaoPlaceDto> kakaoPlaceByKeywordList = kakaoApiService.findPlacesByKeyword(category, kakaoMapDto.CreateRect(), kakaoMapDto.getSearchText(), new ArrayList<>());
            for (KakaoPlaceDto kakaoPlaceDto : kakaoPlaceByKeywordList) {
                for (Document document : kakaoPlaceDto.getDocuments()) {
                    KeywordStringList.add(document.getId());
                }
            }
            placeList.addAll(instagramRepository.findByKeyword(category, kakaoMapDto.CreateRect(), KeywordStringList));
        }

        return placeList;
    }

    @PostMapping("/getHashtagByCount")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        String temp = kakaoMapDto.getCategory();
        String[] categoryList = temp.split(",");

        return instagramRepository.findByHashtagCount(kakaoMapDto.CreateRect(), categoryList, kakaoMapDto.getCheck());
    }

    @PostMapping("/findByHashtagName")
//    @ResponseBody
    public String findByHashtagName(@RequestBody @Valid KakaoMapDto kakaoMapDto, @RequestBody String hashtagName, Model model) {
        String temp = kakaoMapDto.getCategory();
        String[] categoryList = temp.split(",");


        List<PostDto> postDtoList = instagramRepository.findByHashtagName(categoryList, kakaoMapDto.CreateRect(), hashtagName);
        model.addAttribute("postDtoList", postDtoList);

        return "modal/instagramPost";
    }

//    @GetMapping("/getHashtagByCount2")
//    @ResponseBody
//    public List<PlaceDto> getHashtagByCount2(@RequestParam HashMap<String, Object> param) {
//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
//        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
//        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//        String temp = (String) param.get("category_list");
//        String[] categoryList = temp.split(",");
//
//        Map<String, Object> hashtag_count_param = new HashMap<>();
//        hashtag_count_param.put("check1", param.get("check1"));
//        hashtag_count_param.put("check2", param.get("check2"));
//        hashtag_count_param.put("check3", param.get("check3"));
//        hashtag_count_param.put("check4", param.get("check4"));
//        hashtag_count_param.put("check5", param.get("check5"));
//
//        List<PlaceDto> placeList = instagramRepository.getHashtagByCount2(categoryList, hashtag_count_param);
//
//        return placeList;
//    }
}
