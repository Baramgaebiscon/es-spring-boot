package com.early.stage.demo.global.error;

import lombok.Getter;

@Getter
public class ErrorStatusException extends RuntimeException {

    private final ErrorCase errorCase;

    public ErrorStatusException(ErrorCase errorCase) {
        super(errorCase.getMessage());
        this.errorCase = errorCase;
    }

}
