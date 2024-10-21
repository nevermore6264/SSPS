package com.hcmut.ssps_server.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found"),
    USER_EXISTED(1002, "User existed"),
    UNAUTHENTICATED(1003, "Unauthenticated");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
