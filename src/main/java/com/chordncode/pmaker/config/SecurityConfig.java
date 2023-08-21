package com.chordncode.pmaker.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chordncode.pmaker.config.jwt.JwtAuthenticationFilter;
import com.chordncode.pmaker.config.jwt.JwtProvider;

@Configuration
public class SecurityConfig {

    private JwtProvider jwtProvider;
    public SecurityConfig(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(basic -> basic.disable())
                   .csrf(csrf -> csrf.disable())
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeRequests(request -> request.antMatchers("/member/login")
                                                        .permitAll()
                                                        .anyRequest()
                                                        .hasRole("member"))
                   .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                   .exceptionHandling(handle -> handle.accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response,
                                AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                    response.setStatus(403);
                                    response.setContentType("text/html; charset=utf-8");
                                    response.getWriter().write("권한 없음");
                        }
                   }).authenticationEntryPoint(new AuthenticationEntryPoint() {
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response,
                                AuthenticationException authException) throws IOException, ServletException {
                                    response.setStatus(401);
                                    response.setContentType("text/html; charset=utf-8");
                                    response.getWriter().write("인증되지 않은 사용자입니다.");
                        }
                   }))
                   .build();

    }
}
