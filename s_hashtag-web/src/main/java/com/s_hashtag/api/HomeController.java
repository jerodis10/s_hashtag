package com.s_hashtag.api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller
public class HomeController {

//    @GetMapping("/")
//    public String homeLogin() {
//        return "home";
//    }

//    @CrossOrigin
    @GetMapping("/instagramPost")
    public String instagramPost() {
        return "modal/instagramPost";
    }

}