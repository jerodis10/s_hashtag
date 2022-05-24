//package com.s_hashtag.member;
//
//import com.s_hashtag.domain.member.Member;
////import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
//import com.s_hashtag.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//import java.util.Collections;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/members")
//public class MemberController {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @GetMapping("/add")
//    public String addForm(@ModelAttribute("member") Member member) {
//        return "members/addMemberForm";
//    }
//
//    @PostMapping("/add")
//    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "members/addMemberForm";
//        }
//
////        memberRepository.save(member);
//        memberRepository.save(Member.builder()
//                .loginId(member.getLoginId())
//                .password(passwordEncoder.encode(member.getPassword()))
//                .name(member.getName())
//                .role("ROLE_MEMBER")
//                .build());
//
//        return "redirect:/";
//    }
//}
