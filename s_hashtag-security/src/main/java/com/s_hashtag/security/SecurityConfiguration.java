package com.s_hashtag.security;

import com.s_hashtag.security.auth.ApplicationUserService;
import com.s_hashtag.security.jwt.JwtConfig;
import com.s_hashtag.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.crypto.SecretKey;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final AuthenticationFailureHandler customFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // enable h2-console
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
            .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .antMatchers("/", "/api/**", "/css/**", "/img/**", "/js/**").permitAll()
            .anyRequest() .authenticated() // 나머지는 인증 필요

            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureHandler(customFailureHandler) // 로그인 실패 핸들러

            .and()
            .logout()
                .permitAll()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        http.csrf().ignoringAntMatchers("/api/**"); /* REST API 사용 예외처리 */
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
