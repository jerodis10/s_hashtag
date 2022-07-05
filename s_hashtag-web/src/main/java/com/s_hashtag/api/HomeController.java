package com.s_hashtag.api;

import com.s_hashtag.domain.member.Member;
//import com.s_hashtag.instagram.crawler.InstagramCrawler;
//import com.s_hashtag.instagram.dto.CrawlingDto;
//import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
//import com.s_hashtag.kakaoapi.domain.caller.KakaoRestTemplateApiCaller;
//import com.s_hashtag.kakaoapi.domain.dto.Document;
//import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
//import com.s_hashtag.kakaoapi.domain.rect.Rect;
//import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
//import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
//import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
////import com.s_hashtag.kakaoapi.service.KakaoApiService;
//import com.s_hashtag.kakaoapi.service.KakaoApiService;
import com.s_hashtag.repository.MemberRepository;
import com.s_hashtag.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String homeLogin() {
        return "home";
    }

}