package com.s_hashtag.common.domain.member.dto.presentation;

import lombok.*;

import javax.validation.constraints.NotEmpty;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    @NotEmpty
    private String loginId; //로그인 ID

    @NotEmpty
    private String name; //사용자 이름

    @NotEmpty
    private String password;

    private String role;
}
