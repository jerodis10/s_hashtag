//package com.s_hashtag.admin.member.api;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//import java.lang.reflect.Member;
//
//@Controller
////@RequiredArgsConstructor
//@RequestMapping("/api/admin/members")
//public class AdminMemberController {
//
//    @GetMapping("/list")
////    public String addForm(@ModelAttribute Member member) {
//    public String addForm(String s) {
//        return "admin/memberList";
//    }
//
//    @PostMapping("/list")
//    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin/memberList";
//        }
//
//        return "redirect:/";
//    }
//}
