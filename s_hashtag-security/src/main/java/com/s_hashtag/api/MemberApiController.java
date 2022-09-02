package com.s_hashtag.api;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import com.s_hashtag.common.domain.member.repository.MemberRepository;
import com.s_hashtag.security.ApplicationMemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/add")
    public String addForm(@ModelAttribute MemberDto member, Model model) {
        model.addAttribute("applicationMemberRoles", ApplicationMemberRole.values());
        return "members/addMemberForm";
    }

    @Transactional
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute MemberDto member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberRepository.save(MemberDto.builder()
                .loginId(member.getLoginId())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .role("ROLE_" + member.getRole())
                .build());

        return "redirect:/";
    }
}
