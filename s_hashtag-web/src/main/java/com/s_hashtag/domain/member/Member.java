package com.s_hashtag.domain.member;

import lombok.*;

import javax.validation.constraints.NotEmpty;

//@Data
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class Member {

//    @NotEmpty
//    private String id;

    @NotEmpty
    private String loginId; //로그인 ID
    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;
}
