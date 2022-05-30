package com.s_hashtag.admin.member.api;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.lang.reflect.Member;

@Controller
//@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    @GetMapping("/list")
//    public String addForm(@ModelAttribute Member member) {
    public String addForm(String s) {
        return "members/memberList";
    }

    @PostMapping("/list")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        return "redirect:/";
    }

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
//                .role("ROLE_ADMIN")
//                .build());
//
//        return "redirect:/";
//    }
}
