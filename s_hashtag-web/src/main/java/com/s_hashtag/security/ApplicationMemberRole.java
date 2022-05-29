package com.s_hashtag.security;


import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.s_hashtag.security.ApplicationUserPermission.*;

@RequiredArgsConstructor
public enum ApplicationMemberRole {

    MEMBER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(MEMBER_READ, MEMBER_WRITE, BATCH_READ, BATCH_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(MEMBER_READ, BATCH_READ));

    private final Set<ApplicationUserPermission> permissions;

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> grantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
