package com.s_hashtag.api;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
//@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginApiController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") MemberDto member) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto member) {
        return "home";
    }

    @PostMapping("/logout")
    public String logout2(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/courses")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCourses() {
        return "courses";
    }

}
