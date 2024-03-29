package com.early.stage.demo.global.auth;

import com.early.stage.demo.domain.member.entity.Member;
import com.early.stage.demo.global.auth.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    /**
     * In this demo project, use dummy data model
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
            Member.builder()
                .memberId(1L)
                .id(username)
                .password(passwordEncoder.encode("12345**"))
                .role(Role.USER)
                .build()
        );
    }
}
