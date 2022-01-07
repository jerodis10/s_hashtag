//package com.s_hashtag.member;
//
//import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
//import com.s_hashtag.kakaoapi.domain.factory.RectFactory;
//import com.s_hashtag.kakaoapi.domain.rect.Rect;
//import com.s_hashtag.kakaoapi.service.KakaoApiService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class TestService {
//
//    private final KakaoApiService kakaoApiService;
//
//    @GetMapping("/kakao")
//    public void kakao() {
//        Rect rect = RectFactory.from(kakaoEvent.getZone());
//
//        List<KakaoPlaceDto> kakaoPlaceDtos = new ArrayList<>(kakaoApiService.findPlaces("PM9", rect));
//    }
//}
