package com.s_hashtag.kakaoapi.exception;

import com.s_hashtag.common.exception.HashtagMapException;

public class KakaoApiException extends HashtagMapException {
    public KakaoApiException(KakaoApiExceptionStatus kakaoApiExceptionStatus, String detailMessgae) {
        super(kakaoApiExceptionStatus.getMessage(), kakaoApiExceptionStatus.getCode(), detailMessgae);
    }
}
