package com.s_hashtag.login;

import lombok.*;

import javax.validation.constraints.NotEmpty;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
