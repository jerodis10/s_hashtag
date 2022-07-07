//package com.s_hashtag.api;
//
//import com.s_hashtag.login.LoginForm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Slf4j
//@Controller
////@RequestMapping("/api")
//@RequiredArgsConstructor
//public class LoginApiController {
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
//        return "login/loginForm";
//    }
//
//    @PostMapping("/login")
//    public String login(@ModelAttribute LoginForm form) {
//        return "home";
//    }
//
//    @PostMapping("/logout")
//    public String logout2(HttpServletRequest request, HttpServletResponse response) {
//        return "redirect:/";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("/courses")
//    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String getCourses() {
//        return "courses";
//    }
//
//}
