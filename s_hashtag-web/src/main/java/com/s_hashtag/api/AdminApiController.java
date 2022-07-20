package com.s_hashtag.api;

import com.s_hashtag.model.Member;
import com.s_hashtag.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/list")
    public String adminMemberList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "admin/memberList";
    }

    @PostMapping("/members/list")
    public String adminMemberList2(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/memberList";
        }

        return "redirect:/";
    }

    @PostMapping("/members/delete")
    public String adminMemberDelete(@RequestParam("checkedList[]") List<String> checkedList) {
        memberRepository.delete(checkedList);

        return "redirect:/api/admin/members/list";
//        return "admin/memberList";
    }

//    @GetMapping("/getHashtag")
    @ResponseBody
    public String getHashtag() {

        return "23";
    }
}
