//package com.s_hashtag.api;
//
//import com.s_hashtag.domain.member.Member;
//import com.s_hashtag.repository.MemberRepository;
//import com.s_hashtag.security.ApplicationMemberRole;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/api/members")
//public class MemberApiController {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @GetMapping("/add")
//    public String addForm(@ModelAttribute Member member, Model model) {
//        model.addAttribute("applicationMemberRoles", ApplicationMemberRole.values());
//        return "members/addMemberForm";
//    }
//
//    @PostMapping("/add")
//    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "members/addMemberForm";
//        }
//
//        memberRepository.save(Member.builder()
//                .loginId(member.getLoginId())
//                .password(passwordEncoder.encode(member.getPassword()))
//                .name(member.getName())
//                .role("ROLE_" + member.getRole())
//                .build());
//
//        return "redirect:/";
//    }
//}
