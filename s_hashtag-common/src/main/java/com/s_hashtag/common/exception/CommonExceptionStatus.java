package com.s_hashtag.common.exception;

import lombok.Getter;

@Getter
public enum CommonExceptionStatus {
    UNEXPECTED("COMMON_0000", "요청을 처리하지 못했습니다."),
    REQUEST_NOT_ALLOWED("COMMON_0100", "처리할 수 없는 요청 URI입니다."),
    WRONG_ARGUMENT("COMMON_1000", "전달받은 매개변수가 올바르지 않습니다."),
    ALREADY_PERSIST("COMMON_2000", "이미 등록되었습니다."),
    NOT_PERSIST("COMMON_2100", "등록되어있지 않습니다."),
    INVALID_LATITUDE("COMMON_3000", "부정확한 위도 값입니다."),
    INVALID_LONGITUDE("COMMON_3100", "부정확한 경도 값입니다."),
    INVALID_TAG_LEVEL("COMMON_4000", "해시 태그 값이 잘못되었습니다.");

    private final String code;
    private final String message;

    CommonExceptionStatus(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
