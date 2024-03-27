package com.early.stage.demo.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCase {
    _400_BAD_FORM_DATA(HttpStatus.BAD_REQUEST, "400001", "input form data is wrong"),
    _401_LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "401001", "login fail");
    private final HttpStatus status;
    private final String code;
    private final String message;
}