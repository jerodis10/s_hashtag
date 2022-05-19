package com.s_hashtag.security;

import com.s_hashtag.auth.ApplicationUserService;
import com.s_hashtag.jwt.JwtConfig;
import com.s_hashtag.jwt.JwtTokenVerifier;
import com.s_hashtag.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

import static com.s_hashtag.security.ApplicationMemberRole.MEMBER;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic().disable()
            // token을 사용하는 방식이기 때문에 csrf를 disable
            .csrf().disable()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//            // enable h2-console
//            .and()
//            .headers()
//            .frameOptions()
//            .sameOrigin()

            .and()
            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
            .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .antMatchers("/", "index","/css/*", "/js/*").permitAll();
//            .antMatchers("/api/**").hasRole(MEMBER.name())
//            .anyRequest() .authenticated(); // 나머지는 인증 필요
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }
}
