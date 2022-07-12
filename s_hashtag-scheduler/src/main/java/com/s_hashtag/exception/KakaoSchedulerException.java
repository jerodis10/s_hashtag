package com.s_hashtag.exception;

import com.s_hashtag.common.exception.HashtagMapException;

public class KakaoSchedulerException extends HashtagMapException {
    public KakaoSchedulerException(final KakaoSchedulerExceptionStatus kakaoSchedulerExceptionStatus) {
        super(kakaoSchedulerExceptionStatus.getMessage(), kakaoSchedulerExceptionStatus.getCode());
    }
}
