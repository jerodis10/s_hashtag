package com.s_hashtag.security.auth;

import com.s_hashtag.model.Member;
import com.s_hashtag.repository.MemberRepository;
import com.s_hashtag.security.ApplicationMemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

//    private final ApplicationUserDao applicationUserDao;
//
//    @Autowired
//    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
//        this.applicationUserDao = applicationUserDao;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("member %s not found", username))));

        Set<SimpleGrantedAuthority> authority = null;
        for (ApplicationMemberRole role : ApplicationMemberRole.values()) {
            if(member.get().getRole().equals("ROLE_" + role.name())) authority = role.grantedAuthorities();
        }

        return new ApplicationUser(
                member.get().getLoginId(),
//                passwordEncoder.encode(member.get().getPassword()),
                member.get().getPassword(),
                authority,
//                (member.get().getRole().equals("ROLE_" + MEMBER.name()) ?
//                        MEMBER.grantedAuthorities() : ADMIN.grantedAuthorities()),
                true,
                true,
                true,
                true
        );

//        return ApplicationUser.builder()
//                .username(member.get().getLoginId())
//                .password(member.get().getPassword())
//                .grantedAuthorities(MEMBER.grantedAuthorities())
//                .isAccountNonExpired(true)
//                .isAccountNonLocked(true)
//                .isCredentialsNonExpired(true)
//                .isEnabled(true)
//                .build();


//        return applicationUserDao.selectApplicationUserByUsername(username)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
