package com.early.stage.demo.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.ServletApiConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(this::sessionManagementConfigure)
            .headers(this::headersConfigure)
            .csrf(this::csrfConfigure)
            .logout(this::logoutConfigure)
            .servletApi(this::servletApiConfigure)
            .anonymous(this::anonymousConfigure)
            .exceptionHandling(this::exceptionHandlingConfigure);

        return http.build();
    }

    private void sessionManagementConfigure(
        SessionManagementConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void headersConfigure(
        HeadersConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void csrfConfigure(CsrfConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void logoutConfigure(
        LogoutConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void servletApiConfigure(
        ServletApiConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void anonymousConfigure(
        AnonymousConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

    private void exceptionHandlingConfigure(
        ExceptionHandlingConfigurer<HttpSecurity> configure) {
        configure.disable();
    }

}
