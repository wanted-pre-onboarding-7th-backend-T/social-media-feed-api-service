package com.wanted.common.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionCode {
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, " Access Token 만료"),
    INVALID_SIGNATURE_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"서명이 올바르지 않습니다"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
