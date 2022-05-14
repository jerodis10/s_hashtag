//package com.s_hashtag.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository("fake")
//@RequiredArgsConstructor
//public class FakeApplicationUserDaoService implements ApplicationUserDao{
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
//        return getApplicationUsers()
//                .stream()
//                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
//                .findFirst();
//    }
//}
