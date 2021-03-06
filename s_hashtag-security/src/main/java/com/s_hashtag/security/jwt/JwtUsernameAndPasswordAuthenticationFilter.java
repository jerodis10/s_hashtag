package com.s_hashtag.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

//    private final Key key;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
//            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
////                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                    .readValue(request.getInputStream(),
////                        .readValue(request.getReader(),
//                            UsernameAndPasswordAuthenticationRequest.class);

            UsernameAndPasswordAuthenticationRequest authenticationRequest =
                    new UsernameAndPasswordAuthenticationRequest(request.getParameter("loginId"), request.getParameter("password"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLoginId(),
                    authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);

//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);



        String authorizationHeader = response.getHeader(jwtConfig.getAuthorizationHeader());
//        String token2 = authorizationHeader.replace( jwtConfig.getTokenPrefix(), "");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(it -> new SimpleGrantedAuthority(it.get("authority")))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
//                request.getParameter("password"),
                simpleGrantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.sendRedirect("/");

//        filterChain.doFilter(request, response);
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("????????? JWT ???????????????.");
//        } catch (ExpiredJwtException e) {
//            log.info("????????? JWT ???????????????.");
//        } catch (UnsupportedJwtException e) {
//            log.info("???????????? ?????? JWT ???????????????.");
//        } catch (IllegalArgumentException e) {
//            log.info("JWT ????????? ?????????????????????.");
//        }
//        return false;
//    }
}
