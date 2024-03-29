package com.early.stage.demo.domain.member.entity;

import com.early.stage.demo.global.auth.enums.Role;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * dummy class for Member entity
 */

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {

    private Long memberId;
    private String id;
    private String password;
    private Role role;
    public static LocalDateTime lastLoginTime;


}
