package com.s_hashtag.api;

import com.s_hashtag.model.Member;
import com.s_hashtag.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberApiController {

    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public String adminMemberList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "admin/memberList";
    }


    @PostMapping("/delete")
    public String adminMemberDelete(@RequestParam("checkedList[]") List<String> checkedList) {
        memberRepository.delete(checkedList);

        return "redirect:/api/admin/members/list";
//        return "admin/memberList";
    }



//    @PostMapping("/members/list")
//    public String adminMemberList2(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin/memberList";
//        }
//
//        return "redirect:/";
//    }

}
