package com.early.stage.demo.global.config;

import com.early.stage.demo.domain.member.service.MemberLoginService;
import com.early.stage.demo.global.auth.CustomUserDetailsService;
import com.early.stage.demo.global.auth.filter.FormDataLoginAuthenticationFilter;
import com.early.stage.demo.global.auth.filter.JwtAuthenticationFilter;
import com.early.stage.demo.global.auth.filter.RefreshJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberLoginService memberLoginService;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .servletApi(AbstractHttpConfigurer::disable)
            .anonymous(AbstractHttpConfigurer::disable)
            .exceptionHandling(AbstractHttpConfigurer::disable)
            .with(new CustomDsl(), dsl -> {
            });

        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.addFilterAt(new FormDataLoginAuthenticationFilter(http.getSharedObject(
                        AuthenticationManager.class), memberLoginService),
                    UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(customUserDetailsService),
                    FormDataLoginAuthenticationFilter.class)
                .addFilterAfter(new RefreshJwtFilter(memberLoginService),
                    JwtAuthenticationFilter.class);
        }
    }
}
