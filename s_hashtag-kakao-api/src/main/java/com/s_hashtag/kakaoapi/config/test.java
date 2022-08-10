package com.s_hashtag.kakaoapi.config;

import com.s_hashtag.kakaoapi.caller.AbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class test implements ApplicationRunner {

    @Autowired
    AbProperties abProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("=============");
//        System.out.println(abProperties);
//        System.out.println(abProperties.getMaxDocumentCount());
//        System.out.println(abProperties.getKey());
//        System.out.println(abProperties.getBaseUrl());
//        System.out.println("=============");

    }
}


//@AllArgsConstructor
//@RequestMapping("test")
//@RestController
//@Slf4j
//public class test {
//
//    private AbProperties abProperties;
//
//    @GetMapping("/publicKey")
//    public String testPublicKeyProperty() {
//        return abProperties.getBaseUrl();
//    }
//}