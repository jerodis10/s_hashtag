package com.s_hashtag.security;

import com.s_hashtag.auth.ApplicationUserService;
import com.s_hashtag.jwt.JwtConfig;
//import com.s_hashtag.jwt.JwtTokenVerifier;
import com.s_hashtag.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

import static com.s_hashtag.security.ApplicationMemberRole.MEMBER;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
//                .httpBasic().disable()

            // token을 사용하는 방식이기 때문에 csrf를 disable
//            .csrf().disable()

            // 스프링 부트에서의 세션은 유효시간이 15분
            // 세션을 사용하지 않기 때문에 STATELESS로 설정
//            .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()

            // enable h2-console
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
//            .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
            .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
            .antMatchers("/", "/css/**", "/img/**", "/js/**").permitAll()
//            .antMatchers("/login").hasRole(MEMBER.name())
            .anyRequest() .authenticated() // 나머지는 인증 필요

            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
//                .defaultSuccessUrl("/")
//                .failureUrl("")
//                .successHandler(successHandler())
//                .failureHandler(failureHandler())
                .failureHandler(customFailureHandler) // 로그인 실패 핸들러

                .usernameParameter("loginId") // Form tag의 name 값, 기본적으로는 username이 사용
                .passwordParameter("password") // Form tag의 password 값, 기본적으로는 password가 사용

//                .successHandler(
//                        new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                Cookie idCookie = new Cookie("JSESSIONID", URLEncoder.encode(response.getHeader(jwtConfig.getAuthorizationHeader()),"utf-8"));
//                                response.addCookie(idCookie);
//                                response.sendRedirect("/");
//                            }
//                        }
//                )

            .and()
            .logout()
                .permitAll()
//
                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
//                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/");

//        .and().sessionManagement()
//					  .maximumSessions(1) /*session 허용 갯수?*/
//					  .expiredUrl("/login") /* session 만료시 이동 */
//					  .maxSessionsPreventsLogin(true); /* 중복로그인 허용(true),거부(false)

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

//    @Bean
//    public AuthenticationSuccessHandler successHandler() {
//        return new CustomAuthSuccessHandler();
//    }
//
//    @Bean
//    public AuthenticationFailureHandler failureHandler() {
//        return new CustomAuthFailureHandler();
//    }

//    @Bean
//    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
//        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter("/login-process");
//        filter.setAuthenticationManager(new CustomAuthenticationManager());
//        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler("/login"));
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
//        return filter;
//    }

//    @Bean
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("c").password("c").roles("ROLE_MEMBER");
//    }
}
