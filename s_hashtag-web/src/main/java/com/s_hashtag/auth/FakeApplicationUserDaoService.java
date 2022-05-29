package com.s_hashtag.auth;

import com.google.common.collect.Lists;
import com.s_hashtag.domain.member.Member;
import com.s_hashtag.repository.MemberRepository;
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
//    private final MemberRepository memberRepository;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
//        Optional<Member> member = memberRepository.findById(username);
//        return ApplicationUser.builder()
//                .username(Optional.ofNullable(getLoginId))

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
                ),
            new ApplicationUser(
                    "b",
                    passwordEncoder.encode("b"),
                    MEMBER.grantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ),
            new ApplicationUser(
                    "master",
                    passwordEncoder.encode("master"),
                    ADMIN.grantedAuthorities(),
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
