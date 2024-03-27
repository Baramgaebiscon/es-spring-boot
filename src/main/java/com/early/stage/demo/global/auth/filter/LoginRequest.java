package com.early.stage.demo.global.auth.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String id;
    private String password;
}
