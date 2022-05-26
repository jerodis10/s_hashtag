package com.s_hashtag.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.s_hashtag.security.ApplicationMemberRole.ADMIN;
import static com.s_hashtag.security.ApplicationMemberRole.MEMBER;

@Repository("fake")
@RequiredArgsConstructor
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "annasmith",
                        passwordEncoder.encode("password"),
                        MEMBER.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("password"),
                        ADMIN.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "c",
                        passwordEncoder.encode("c"),
                        MEMBER.grantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
//                new ApplicationUser(
//                        "tom",
//                        passwordEncoder.encode("password"),
//                        ADMINTRAINEE.grantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                )
        );

        return applicationUsers;
    }
}
