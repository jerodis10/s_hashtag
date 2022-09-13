package com.s_hashtag.api;

import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.instagram.service.InstagramService;
import com.s_hashtag.kakaoapi.caller.KakaoProperties;
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

        return instagramRepository.findAll(kakaoMapDto.getCategoryList(), kakaoMapDto.CreateRect());
    }

    @PostMapping("/getHashtagByKeyword")
    @ResponseBody
    public List<PlaceDto> getHashtagByKeyword(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        List<PlaceDto> placeList = new ArrayList<>();

        for(String category : kakaoMapDto.getCategoryList()) {
            List<String> KeywordStringList = new ArrayList<>();

            List<KakaoPlaceDto> kakaoPlaceByKeywordList = kakaoApiService.findPlacesByKeyword(category, kakaoMapDto.CreateRect(), kakaoMapDto.getSearchText(), new ArrayList<>());
            for (KakaoPlaceDto kakaoPlaceDto : kakaoPlaceByKeywordList) {
                for (Document document : kakaoPlaceDto.getDocuments()) {
                    KeywordStringList.add(document.getId());
                }
            }
            placeList.addAll(instagramRepository.findByKeyword(kakaoMapDto.getCategoryList(), kakaoMapDto.CreateRect(), KeywordStringList));
        }

        return placeList;
    }

    @PostMapping("/getHashtagByCount")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount(@RequestBody @Valid KakaoMapDto kakaoMapDto, BindingResult errors) {
        return instagramRepository.findByHashtagCount(kakaoMapDto.CreateRect(), kakaoMapDto.getCategoryList(), kakaoMapDto.getCheck());
    }

    @PostMapping("/findByHashtagName")
    @ResponseBody
    public List<PostDto> findByHashtagName(@RequestBody @Valid KakaoMapDto kakaoMapDto, Model model) {
        List<PostDto> postDtoList = instagramRepository.findByHashtagName(kakaoMapDto.getCategoryList(), kakaoMapDto.CreateRect(), kakaoMapDto.getHashtagName());
        model.addAttribute("postDtoList", postDtoList);

        return postDtoList;
    }

}
