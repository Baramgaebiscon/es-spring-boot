package com.early.stage.demo.global.auth;

import com.early.stage.demo.domain.member.entity.Member;
import com.early.stage.demo.global.auth.enums.Role;
import com.early.stage.demo.global.error.ErrorStatusException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * In this demo project, use dummy data model
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
            Member.builder()
                .memberId(1L)
                .id(username)
                .password((new BCryptPasswordEncoder()).encode("12345**"))
                .role(Role.USER)
                .build()
        );
    }

    /**
     * In this demo project, use dummy data model
     */
    public UserDetails loadUserByUserId(Long userId) throws ErrorStatusException {
        return new CustomUserDetails(
            Member.builder()
                .memberId(userId)
                .id("test_id")
                .password((new BCryptPasswordEncoder()).encode("12345**"))
                .role(Role.USER)
                .build()
        );
    }
}
