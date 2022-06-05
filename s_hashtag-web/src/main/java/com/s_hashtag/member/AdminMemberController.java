package com.s_hashtag.member;

import com.s_hashtag.domain.member.Member;
import com.s_hashtag.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public String adminMemberList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "admin/memberList";
    }

    @PostMapping("/list")
    public String adminMemberList2(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/memberList";
        }

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String adminMemberDelete(@RequestParam("checkedList[]") List<String> checkedList) {
        memberRepository.delete(checkedList);

        return "redirect:/api/admin/members/list";
//        return "admin/memberList";
    }
}