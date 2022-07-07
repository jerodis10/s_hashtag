package com.s_hashtag.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UsernameAndPasswordAuthenticationRequest {

//    private String username;
    private String loginId;
    private String password;

}
