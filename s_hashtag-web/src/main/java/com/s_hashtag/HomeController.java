package com.s_hashtag;

import com.s_hashtag.domain.member.Member;
import com.s_hashtag.kakaoapi.domain.caller.KakaoProperties;
import com.s_hashtag.kakaoapi.domain.caller.KakaoRestTemplateApiCaller;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
//import com.s_hashtag.kakaoapi.service.KakaoApiService;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    private final KakaoRestTemplateApiCaller kakaoRestTemplateApiCaller;
    private final KakaoApiService kakaoApiService;

//    private final KakaoProperties kakaoProperties;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) String memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        //로그인
        Optional<Member> loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        //로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
//    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
//
//        //세션에 회원 데이터가 없으면 home
//        if (loginMember == null) {
//            return "home";
//        }
//
//        //세션이 유지되면 로그인으로 이동
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }

    @PostMapping("/kakaoMap")
    @ResponseBody
//    public KakaoPlaceDto kakaoMap(@RequestParam Map<String, Object> param) {
    public List<KakaoPlaceDto> kakaoMap(@RequestParam Map<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));

        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//        Rect rect = new Rect(param.get("ha"), param.get("oa"), param.get("ha"), param.get("ha"));
//        KakaoPlaceDto  kakaoPlaceDto= kakaoRestTemplateApiCaller.findPlaceByCategory("FD6", rect, 1);
        List<KakaoPlaceDto>  kakaoPlaceDto= kakaoApiService.findPlaces("FD6", rect, new ArrayList<>());
        return kakaoPlaceDto;
    }
}