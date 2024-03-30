package com.early.stage.demo.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCase {
    _400_BAD_FORM_DATA(HttpStatus.BAD_REQUEST, "400001", "input form data is wrong"),
    _401_LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "401001", "login fail"),
    _401_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "401002", "token is expired"),
    _401_TOKEN_VALIDATE_FAIL(HttpStatus.UNAUTHORIZED, "401003", "token validation is fail"),
    _401_NOT_MOST_RECENT_GENERATED_TOKEN(HttpStatus.UNAUTHORIZED, "401004",
        "this refresh token is not most recent generated"),
    _500_JWT_GENERATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "500001", "jwt token generation fail");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
