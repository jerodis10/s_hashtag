package com.s_hashtag.api;

import com.s_hashtag.login.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginApiController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginV5(@ModelAttribute LoginForm form) {
        return "home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return "redirect:/";
    }

    @GetMapping("/courses")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCourses() {
        return "courses";
    }

}
