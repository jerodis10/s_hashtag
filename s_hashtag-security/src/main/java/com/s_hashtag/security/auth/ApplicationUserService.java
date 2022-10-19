package com.s_hashtag.security.auth;

import com.s_hashtag.common.domain.member.dto.presentation.MemberDto;
import com.s_hashtag.common.domain.member.repository.MemberRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberDto> member = Optional.ofNullable(memberRepository.findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("member %s not found", username))));

        Set<SimpleGrantedAuthority> authority = null;
        for (ApplicationMemberRole role : ApplicationMemberRole.values()) {
            if(member.get().getRole().equals("ROLE_" + role.name())) authority = role.grantedAuthorities();
        }

        return new ApplicationUser(
                member.get().getLoginId(),
                member.get().getPassword(),
                authority,
                true,
                true,
                true,
                true
        );
    }
}
